package threads;

import utils.HttpUtils;

import java.util.concurrent.Callable;

public class MyCallable implements Callable<String> {
    private String url;
    public MyCallable(String url) {
        this.url = url;
    }
    @Override
    public String call() throws Exception {
        return HttpUtils.fetchData(url);
    }
}