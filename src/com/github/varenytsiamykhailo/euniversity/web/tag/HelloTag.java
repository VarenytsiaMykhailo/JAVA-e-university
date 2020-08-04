package com.github.varenytsiamykhailo.euniversity.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public final class HelloTag extends TagSupport {
    private String name = null;

    @Override
    public int doStartTag() throws JspException {
        try {
            if (name == null) {
                pageContext.getOut().write("Hello, world!");
            } else {
                pageContext.getOut().write("Hello, " + name);
            }
        } catch (IOException e) {
            throw new JspTagException(e.getMessage());
        }
        return SKIP_BODY;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void release() {
        super.release();
        name = null;
    }
}
