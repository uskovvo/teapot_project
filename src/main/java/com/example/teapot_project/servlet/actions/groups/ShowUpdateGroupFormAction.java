package com.example.teapot_project.servlet.actions.groups;

import com.example.teapot_project.servlet.actions.ServletAction;

import javax.servlet.http.HttpServletRequest;

public class ShowUpdateGroupFormAction extends AbstractGroupFormAction{

    @Override
    protected void addGroupToRequest(HttpServletRequest request) {
        request.setAttribute("group", groupRepository.read(getId(request)));
    }
}
