package com.allknu.backend.global.crawling;

import java.io.IOException;

public interface Downloader<T> {

    T execute() throws IOException;
}
