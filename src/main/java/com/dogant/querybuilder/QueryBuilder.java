package com.dogant.querybuilder;

import com.dogant.criteria.Criteria;
import com.dogant.criteria.Item;
import com.dogant.request.Request;
import com.dogant.selection.Axis;
import com.dogant.selection.Selection;
import com.dogant.util.Const;

public class QueryBuilder {

   SelectBuilder sql = new SelectBuilder();
   Request request = new Request();

   public QueryBuilder(Request request) {
      this.request = request;
   }

   @Override
   public String toString() {

      return sql.toString();
   }

   public String getSQL() {

      if (request == null || (!request.hasCriteria() && !request.hasSelection()))
         return "";

      return getSelection().getFrom().getCriteria().toString();
   }

   private QueryBuilder getSelection() {

      if (request.hasSelection()) {

         Selection s = request.getSelection();

         for (Axis axis : s.getAxes()) {
            if (axis != null && !axis.getColumn().isEmpty()) {
               sql.column(axis.getColumn(), true);
            }
         }
      }

      return this;
   }

   private QueryBuilder getCriteria() {

      if (request.hasCriteria()) {
         Criteria c = request.getCriteria();

         for (Item item : c.getItems()) {

            if (item != null && !item.getWhereClause().isEmpty()) {
               sql.where(item.getWhereClause());
            }
         }
      }
      return this;
   }

   private QueryBuilder getFrom() {

      // TODO implement table matrix and join logic
      sql.from(Const.getTable());
      return this;
   }

}
