package org.vertexium.cypher.functions.list;

import org.vertexium.Element;
import org.vertexium.cypher.VertexiumCypherQueryContext;
import org.vertexium.cypher.ast.model.CypherAstBase;
import org.vertexium.cypher.exceptions.VertexiumCypherTypeErrorException;
import org.vertexium.cypher.executor.ExpressionScope;
import org.vertexium.cypher.functions.CypherFunction;

import java.util.Map;

public class KeysFunction extends CypherFunction {
    @Override
    public Object invoke(VertexiumCypherQueryContext ctx, CypherAstBase[] arguments, ExpressionScope scope) {
        assertArgumentCount(arguments, 1);
        Object arg0 = ctx.getExpressionExecutor().executeExpression(ctx, arguments[0], scope);

        if (arg0 instanceof Element) {
            return ctx.getKeys((Element) arg0);
        }

        if (arg0 instanceof Map) {
            return ((Map) arg0).keySet();
        }

        throw new VertexiumCypherTypeErrorException(arg0, Element.class, Map.class);
    }
}
