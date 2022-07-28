package com.aj.stocks.thread;

import com.aj.stocks.bean.Stockfeed;

import java.util.List;
import java.util.concurrent.Callable;

public interface FeedThread extends Callable<List<Stockfeed>> {}
