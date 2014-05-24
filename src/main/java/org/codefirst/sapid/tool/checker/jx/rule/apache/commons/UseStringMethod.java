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
            JxExpression reciever = first(dotExpr.getChildren(JxExpression.class));
            if (reciever == null
                    || reciever.getSort() != JxExpression.Sort.VAR_REF) {
                continue;
            }
            JxIdentifier ident = first(reciever.getChildren(JxIdentifier.class));
            if (ident == null) {
                continue;
            }
            JxId defId = ident.getDefId();
            IJxObject def = ((AJxNodeObject) model).getDescendant(defId);
            if (def == null || !(def instanceof AJxNodeObject)) {
                continue;
            }
            JxType type = first(((AJxNodeObject) def).getDescendants(JxType.class));
            if (String.class.getCanonicalName().equals(type.getFqnAsString())) {
                addViolation(newViolation((AJxNodeObject) expr));
            }
        }
    }

    protected <T> T first(SortedSet<T> set) {
        if (set.isEmpty()) {
            return null;
        }
        return set.first();
    }
}
