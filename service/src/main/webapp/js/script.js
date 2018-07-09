$(document).ready(function () {
    $(document).ajaxStart(function () {
        $("#wait").css("display", "block");

    });
    $(document).ajaxComplete(function () {
        $("#wait").css("display", "none");
    });

    $("form").submit(function (event) {
        $("#resultTableId").hide();
        $("#linkTableId").hide();
        $("#errorTableId").hide();

        var rootUrl = "http://localhost:8080/service/services/rest";
        var restApi = rootUrl + "/api/analyze?url=" + encodeURI($("#urlId").val());


        $.getJSON(restApi, {})
            .done(function (json) {
                var s = JSON.stringify(json);
                console.log("JSON Data: " + s);
                setValuesInTable(json);

                $("#resultTableId").show();

            })
            .fail(function (jqxhr, textStatus, error) {
                //var error = JSON.stringify(error);
                //console.log("JSON Data: " + s);
                $("#exceptionId").html(jqxhr.responseJSON.value);
                $("#errorTableId").show();
            });

        return false;
    });

    function setValuesInTable(json) {
        $("#versionId").html(json.htmlVersion);
        $("#pageTitleId").html(json.title);

        $("#h1CountId").html(json.headers.h1);
        $("#h2CountId").html(json.headers.h2);
        $("#h3CountId").html(json.headers.h3);
        $("#h4CountId").html(json.headers.h4);
        $("#h5CountId").html(json.headers.h5);
        $("#h6CountId").html(json.headers.h6);

        $("#internalLinkCountId").html(json.linkCounts.internal);
        var e = json.linkCounts.external;
        $("#externalLinkCountId").html(e);
        if (e > 0) {
            $("#link_id").html("Explore External Link");
        } else {
            $("#link_id").html("");
        }
        $("#hasLoginFormId").html(json.hasLogin == true ? "Yes" : "No");
    }

    $("#link_id").click(function () {
        //Do stuff when clicked
        var rootUrl = "http://localhost:8080/service/services/rest";
        var restApi = rootUrl + "/api/external?url=" + encodeURI($("#urlId").val());


        $.getJSON(restApi, {})
            .done(function (json) {
                $.each(json, function (i, item) {
                    var $tr = $('<tr>').append(
                        $('<td style="width:100px; word-wrap:break-word;">').text(item.url),
                        $('<td>').text(item.reachable),
                        $('<td>').text(item.failureReason)
                    ).appendTo('#linkTableId');
                    //console.log($tr.wrap('<p>').html());
                });

                $("#linkTableId").show();

            })
            .fail(function (jqxhr, textStatus, error) {
                //var error = JSON.stringify(error);
                //console.log("JSON Data: " + s);
                $("#exceptionId").html(jqxhr.responseJSON.value);
                $("#errorTableId").show();
            });

        return false;
    });

// Attach a delegated event handler with a more refined selector
    /* $("#link_id").on("click", "a", function (event) {
         event.preventDefault();
         console.log("hello");
     });*/

});

