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

    public String build(String title, String name, String top, String action_target, String action_name, String bottom, boolean action) {
        Context context = new Context();
        context.setVariable("has_action", action);
        context.setVariable("title", title);
        context.setVariable("name", name);
        context.setVariable("message_top", top);
        context.setVariable("action_target", action_target);
        context.setVariable("action_name", action_name);
        context.setVariable("message_bottom", bottom);
        return templateEngine.process("mailtemplate", context);
    }

}