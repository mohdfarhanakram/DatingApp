package com.farru.android.parser;

import com.farru.android.network.ServiceResponse;

/**
 * Created  on 31/1/14.
 */
public interface IParser {
    public ServiceResponse parseData(int eventType,String data);

}
