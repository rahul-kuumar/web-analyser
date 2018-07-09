package com.ms.challenge;


import com.ms.challenge.entity.AnalysisOutput;
import com.ms.challenge.entity.ExternalLinkDetails;
import com.ms.challenge.error.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/")
public final class WebAnalysisService {
    private final static Logger logger = LoggerFactory.getLogger(WebAnalysisService.class);

    private final IWebAnalysisServiceProxy proxy;

    public WebAnalysisService(IWebAnalysisServiceProxy proxy) {
        logger.info("WebAnalysisService is started.");
        this.proxy = proxy;
    }

    @GET
    @Path("/api/analyze")
    public Response analyzeWebPage(@QueryParam("url") String url) {
        logger.info("Start analysing {} details. ", url);

        try {
            AnalysisOutput analysisOutput = proxy.analyzeWebPage(url);
            return Response.ok(JSONUtils.toJson(analysisOutput), MediaType.APPLICATION_JSON_TYPE).build();
        } catch (BadRequestException | IllegalArgumentException e) {
            return JSONUtils.createJsonResponse(Response.Status.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return JSONUtils.createJsonResponse(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }


    @GET
    @Path("/api/external")
    public Response analyzeExternalLinks(@QueryParam("url") String url) {
        logger.info("Start analysing external links {} details. ", url);
        try {
            List<ExternalLinkDetails> externalLinkDetails = proxy.analyzeExternalLinks(url);
            return Response.ok(JSONUtils.toJson(externalLinkDetails), MediaType.APPLICATION_JSON_TYPE).build();
        } catch (BadRequestException | IllegalArgumentException e) {
            return JSONUtils.createJsonResponse(Response.Status.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return JSONUtils.createJsonResponse(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


}
