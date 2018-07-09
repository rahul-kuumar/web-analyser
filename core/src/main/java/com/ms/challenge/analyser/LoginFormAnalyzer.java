package com.ms.challenge.analyser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginFormAnalyzer implements ILoginFormAnalyzer {
    private final static Logger logger = LoggerFactory.getLogger(LoginFormAnalyzer.class);
    private final Document document;

    public LoginFormAnalyzer(Document document) {
        this.document = document;
    }

    @Override
    public boolean hasLoginForm() {
        logger.info("Looking for the login form");

        Elements forms = document.select("form");

        for (Element form : forms) {
            if (isLoginForm(form)) {
                return true;
            }

        }
        logger.info("Login form was not found");
        return false;

    }


    private boolean isLoginForm(Element form) {
        Elements passwords = form.select("input[type=password]");
        if (passwords.size() != 1) {
            return false;
        }
        Elements textInputs = form.select("input[type=text]");
        Elements emailInputs = form.select("input[type=email]");
        return ((textInputs.size() == 1) ^ (emailInputs.size() == 1));


    }
}
