package org.acme.ticket_support;

/**
 * This class was automatically generated by the data modeler tool.
 */

public class IssueList implements java.io.Serializable
{

   static final long serialVersionUID = 1L;

   @org.kie.api.definition.type.Label(value = "Issue list")
   private java.util.List<org.acme.ticket_support.Issue> issueList;

   public IssueList()
   {
   }

   public java.util.List<org.acme.ticket_support.Issue> getIssueList()
   {
      return this.issueList;
   }

   public void setIssueList(
         java.util.List<org.acme.ticket_support.Issue> issueList)
   {
      this.issueList = issueList;
   }

   public IssueList(java.util.List<org.acme.ticket_support.Issue> issueList)
   {
      this.issueList = issueList;
   }

}