package com.tylx.leasephone.model.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class BadIntegerDeserializer implements JsonDeserializer<Integer> {

    @Override
    public Integer deserialize(JsonElement element, Type type,
                               JsonDeserializationContext context) throws JsonParseException {
        try {
            if (element.getAsString().equals("") || element.getAsString().equals("null"))
                return 0;
//            System.out.println("走 Integer.parseInt(element.getAsString()) 返回");
//            return Integer.parseInt(element.getAsString());
            return element.getAsInt();
        } catch (NumberFormatException e) {
            return 0;
        }
    }

}