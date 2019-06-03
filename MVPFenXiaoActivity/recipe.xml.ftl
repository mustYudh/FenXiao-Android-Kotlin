<?xml version="1.0"?>
<recipe>

    <#include "../common/recipe_theme.xml.ftl" />

    <merge from="root/AndroidManifest.xml.ftl"
             to="${escapeXmlAttribute(manifestOut)}/AndroidManifest.xml" />

    <instantiate from="root/res/layout/activity_new.xml.ftl"
                   to="${escapeXmlAttribute(resOut)}/layout/${layoutName}_view.xml" />

    <instantiate from="root/src/app_package/NewActivity.java.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${activityClass}Activity.java" />

    <instantiate from="root/src/app_package/presenter/NewPresenter.java.ftl"
                   to="${escapeXmlAttribute(srcOut)}/presenter/${activityClass}Presenter.java" />
    <instantiate from="root/src/app_package/presenter/NewViewer.java.ftl"
                   to="${escapeXmlAttribute(srcOut)}/presenter/${activityClass}Viewer.java" />

    <open file="${escapeXmlAttribute(srcOut)}/${activityClass}.java" />

</recipe>
