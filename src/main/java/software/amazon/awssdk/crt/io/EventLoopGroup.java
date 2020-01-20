/*
 * Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package software.amazon.awssdk.crt.io;

import java.util.concurrent.CompletableFuture;
import software.amazon.awssdk.crt.CrtResource;
import software.amazon.awssdk.crt.CrtRuntimeException;
import software.amazon.awssdk.crt.Log;

import java.util.function.Consumer;

/**
 * This class wraps the aws_event_loop_group from aws-c-io to provide
 * access to an event loop for the MQTT protocol stack in the AWS Common
 * Runtime.
 */
public final class EventLoopGroup extends CrtResource {

    private final CompletableFuture<Void> shutdownComplete = new CompletableFuture<>();

    /**
     * Creates a new event loop group for the I/O subsystem to use to run blocking I/O requests
     * @param numThreads The number of threads that the event loop group may run tasks across. Usually 1.
     * @throws CrtRuntimeException If the system is unable to allocate space for a native event loop group
     */
    public EventLoopGroup(int numThreads) throws CrtRuntimeException {
        //acquireNativeHandle(eventLoopGroupNew(this, shutdownComplete, numThreads), (elg)->eventLoopGroupDestroy(elg));
        acquireNativeHandle(eventLoopGroupNew(this, shutdownComplete, numThreads), EventLoopGroup::eventLoopGroupDestroy);
    }

    public CompletableFuture<Void> getShutdownCompleteFuture() { return shutdownComplete; }

    /*******************************************************************************
     * native methods
     ******************************************************************************/
    private static native long eventLoopGroupNew(EventLoopGroup thisObj, CompletableFuture<Void> shutdownComplete, int numThreads) throws CrtRuntimeException;
    private static native void eventLoopGroupDestroy(long elg);
};
