
package com.springessentialsbook.chapter7.struts;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;

import com.opensymphony.xwork2.ActionSupport;

@Action("/order")
@ResultPath("/WEB-INF/pages")
@Result(name = "success", location = "orderEntryForm.jsp")
public class ViewOrderAction extends ActionSupport {
    @Override
    public String execute() throws Exception {
        return super.execute();
    }
}
