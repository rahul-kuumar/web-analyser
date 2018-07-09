package com.ms.challenge;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.challenge.error.BadRequestException;
import org.apache.commons.lang3.StringEscapeUtils;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

final class JSONUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static String toJson(Object result) {
        String value;
        try {
            value = OBJECT_MAPPER.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            throw new BadRequestException(e);
        }
        return value;
    }

    public static Response createJsonResponse(Response.Status status, String message) {
        message = StringEscapeUtils.escapeJson(message);
        return Response.status(status)
                .entity("{\"value\":\"" + message + "\"}")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
