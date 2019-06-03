<?xml version="1.0"?>
<recipe>

    <#include "../common/recipe_theme.xml.ftl" />


    <instantiate from="root/res/layout/fragment_new.xml.ftl"
                   to="${escapeXmlAttribute(resOut)}/layout/${layoutName}_view.xml" />

    <instantiate from="root/src/app_package/NewFragment.java.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${fragmentClass}Fragment.java" />

    <instantiate from="root/src/app_package/presenter/NewPresenter.java.ftl"
                   to="${escapeXmlAttribute(srcOut)}/presenter/${fragmentClass}Presenter.java" />
    <instantiate from="root/src/app_package/presenter/NewViewer.java.ftl"
                   to="${escapeXmlAttribute(srcOut)}/presenter/${fragmentClass}Viewer.java" />

    <open file="${escapeXmlAttribute(srcOut)}/${fragmentClass}.java" />

</recipe>
