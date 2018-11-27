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
package software.amazon.awssdk.crt;

import software.amazon.awssdk.crt.CRT;
import software.amazon.awssdk.crt.CrtRuntimeException;
import software.amazon.awssdk.crt.CrtResource;

import java.io.Closeable;

/**
 * This class wraps the aws_tls_connection_options from aws-c-io to provide
 * access to TLS configuration contexts in the AWS Common Runtime.
 */
public final class TlsContext extends CrtResource implements Closeable {

    public TlsContext(TlsContextOptions options) throws CrtRuntimeException {
        acquire(tls_ctx_new(options.native_ptr()));
    }

    @Override
    public void close() {
        if (native_ptr() != 0) {
            tls_ctx_clean_up(release());
        }
    }

    /*******************************************************************************
     * native methods
     ******************************************************************************/
    private static native long tls_ctx_new(long options) throws CrtRuntimeException;

    private static native void tls_ctx_clean_up(long elg);
};