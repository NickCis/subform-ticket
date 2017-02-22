package org.acme.ticket_support;

/**
 * This class was automatically generated by the data modeler tool.
 */

public class Ticket implements java.io.Serializable
{

   static final long serialVersionUID = 1L;

   @org.kie.api.definition.type.Label("ticket title")
   private java.lang.String title;
   @org.kie.api.definition.type.Label("ticket description")
   private java.lang.String description;
   @org.kie.api.definition.type.Label("ticket issue")
   private org.acme.ticket_support.Issue issue;

   public Ticket()
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

   public org.acme.ticket_support.Issue getIssue()
   {
      return this.issue;
   }

   public void setIssue(org.acme.ticket_support.Issue issue)
   {
      this.issue = issue;
   }

   public String toString()
   {
      return "{'title': '" + this.title + "', " +
            "'description': '" + this.description + "', " +
            "'issue': '" + this.issue + "'}";
   }

   public Ticket(java.lang.String title, java.lang.String description,
         org.acme.ticket_support.Issue issue)
   {
      this.title = title;
      this.description = description;
      this.issue = issue;
   }

}