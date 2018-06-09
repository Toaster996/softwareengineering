package de.dhbw.softwareengineering.digitaljournal.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailContentBuilder {

    private TemplateEngine templateEngine;

    @Autowired
    public MailContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String build(String title, String name, String top, String actionTarget, String actionName, String bottom) {
        Context context = new Context();
        context.setVariable("title", title);
        context.setVariable("name", name);
        context.setVariable("messageTop", top);
        context.setVariable("actionTarget", actionTarget);
        context.setVariable("actionName", actionName);
        context.setVariable("messageBottom", bottom);
        return templateEngine.process("mailtemplate", context);
    }

}