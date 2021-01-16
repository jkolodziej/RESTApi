/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pas.rest.adapters;

import javax.json.Json;
import javax.json.JsonValue;
import javax.json.bind.adapter.JsonbAdapter;

/**
 *
 * @author student
 */
public class SerializeStringToEmptyAdapter implements JsonbAdapter<String, JsonValue> {

    @Override
    public JsonValue adaptToJson(String arg0) throws Exception {
        return Json.createValue("");
    }

    @Override
    public String adaptFromJson(JsonValue json) throws Exception {
        return json.toString();
    }
    
}
