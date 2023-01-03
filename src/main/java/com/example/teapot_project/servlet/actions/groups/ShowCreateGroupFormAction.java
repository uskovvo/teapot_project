package com.example.teapot_project.servlet.actions.groups;

import com.example.teapot_project.model.Group;

import javax.servlet.http.HttpServletRequest;

public class ShowCreateGroupFormAction extends AbstractGroupFormAction {
    @Override
    protected void addGroupToRequest(HttpServletRequest request) {
        request.setAttribute("group", new Group());

    }
}
