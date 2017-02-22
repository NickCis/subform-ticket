package org.acme.ticket_support;

/**
 * This class was automatically generated by the data modeler tool.
 */

public class Issue implements java.io.Serializable
{

   static final long serialVersionUID = 1L;

   @org.kie.api.definition.type.Label("issue title")
   private java.lang.String title;
   @org.kie.api.definition.type.Label("issue description")
   private java.lang.String description;
   @org.kie.api.definition.type.Label("application name")
   private java.lang.String applicationName;
   @org.kie.api.definition.type.Label("application version")
   private java.lang.String applicationVersion;
   @org.kie.api.definition.type.Label("issue severity")
   private java.lang.String severity;

   @org.kie.api.definition.type.Label(value = "issue components")
   private java.util.List<org.acme.ticket_support.Component> components;

   public Issue()
   {
   }

   public java.lang.String getTitle()
   {
      return this.title;
   }

   public void setTitle(java.lang.String title)
   {
      this.title = title;
   }

   public java.lang.String getDescription()
   {
      return this.description;
   }

   public void setDescription(java.lang.String description)
   {
      this.description = description;
   }

   public java.lang.String getApplicationName()
   {
      return this.applicationName;
   }

   public void setApplicationName(java.lang.String applicationName)
   {
      this.applicationName = applicationName;
   }

   public java.lang.String getApplicationVersion()
   {
      return this.applicationVersion;
   }

   public void setApplicationVersion(java.lang.String applicationVersion)
   {
      this.applicationVersion = applicationVersion;
   }

   public java.lang.String getSeverity()
   {
      return this.severity;
   }

   public void setSeverity(java.lang.String severity)
   {
      this.severity = severity;
   }

   public java.util.List<org.acme.ticket_support.Component> getComponents()
   {
      return this.components;
   }

   public void setComponents(
         java.util.List<org.acme.ticket_support.Component> components)
   {
      this.components = components;
   }

   public Issue(java.lang.String title, java.lang.String description,
         java.lang.String applicationName, java.lang.String applicationVersion,
         java.lang.String severity,
         java.util.List<org.acme.ticket_support.Component> components)
   {
      this.title = title;
      this.description = description;
      this.applicationName = applicationName;
      this.applicationVersion = applicationVersion;
      this.severity = severity;
      this.components = components;
   }
   
   public String toString()
   {
       return "{'title': '" + this.title + "', " +
       "'description': '" + this.description + "', " +
       "'applicationVersion': '" + this.applicationVersion +  "', " +
       "'severity': '" + this.severity +  "', " +
       "'components': '" + this.components + "'}";
   }

}