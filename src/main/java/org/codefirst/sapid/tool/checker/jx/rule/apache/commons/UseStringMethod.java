package org.codefirst.sapid.tool.checker.jx.rule.apache.commons;

import java.util.SortedSet;

import org.sapid.ar.jxmodel.AJxNodeObject;
import org.sapid.ar.jxmodel.IJxObject;
import org.sapid.ar.jxmodel.JxExpression;
import org.sapid.ar.jxmodel.JxId;
import org.sapid.ar.jxmodel.JxIdentifier;
import org.sapid.ar.jxmodel.JxType;
import org.sapid.tool.checker.RuleApplicationException;
import org.sapid.tool.checker.jx.rule.AbstractJxRule;

public class UseStringMethod extends AbstractJxRule {

    @Override
    protected void check(IJxObject model) throws RuleApplicationException {
        SortedSet<JxExpression> expressions = ((AJxNodeObject) model)
                .getDescendants(JxExpression.class);
        for (JxExpression expr : expressions) {
            if (expr.getSort() != JxExpression.Sort.METHOD_CALL) {
                continue;
            }
            if (!(expr.getParent() instanceof JxExpression)) {
                continue;
            }
            JxExpression dotExpr = (JxExpression) expr.getParent();
            if (dotExpr.getSort() != JxExpression.Sort.DOT) {
                continue;
            }
            JxExpression reciever = dotExpr.getChildren(JxExpression.class)
                    .first();
            if (reciever == null
                    || reciever.getSort() != JxExpression.Sort.VAR_REF) {
                continue;
            }
            JxIdentifier ident = reciever.getChildren(JxIdentifier.class)
                    .first();
            if (ident == null) {
                continue;
            }
            JxId defId = ident.getDefId();
            IJxObject def = ((AJxNodeObject) model).getDescendant(defId);
            if (def == null || !(def instanceof AJxNodeObject)) {
                continue;
            }
            JxType type = ((AJxNodeObject) def).getDescendants(JxType.class)
                    .first();
            if (String.class.getCanonicalName().equals(type.getFqnAsString())) {
                addViolation(newViolation((AJxNodeObject) expr));
            }
        }
    }
}
