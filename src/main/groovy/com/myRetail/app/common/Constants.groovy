package com.myRetail.app.common

class Constants {
    static enum Headers {
        HTTP_HEADER_CLIENT_ID('X-CLIENT-ID'),
        HTTP_HEADER_CLIENT_SECRET('X-CLIENT-SECRET')

        String value

        Headers(String str) {
            this.value = str
        }

        @Override
        String toString() {
            this.value
        }
    }
}
