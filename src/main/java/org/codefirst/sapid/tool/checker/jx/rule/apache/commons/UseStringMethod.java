package org.codefirst.sapid.tool.checker.jx.rule.apache.commons;

import org.sapid.ar.jxmodel.AJxNodeObject;
import org.sapid.ar.jxmodel.IJxObject;
import org.sapid.tool.checker.RuleApplicationException;
import org.sapid.tool.checker.jx.rule.AbstractJxRule;

public class UseStringMethod extends AbstractJxRule {

    @Override
    protected void check(IJxObject model) throws RuleApplicationException {
        // TODO
        addViolation(newViolation((AJxNodeObject) model));
    }

}
