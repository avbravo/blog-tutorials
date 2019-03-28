package de.rieckpil.blog;

import java.util.List;

import org.hibernate.QueryException;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.BooleanType;
import org.hibernate.type.Type;

public class PostgreSQLFTSFunction implements SQLFunction {

	@Override
	public Type getReturnType(Type columnType, Mapping mapping) throws QueryException {
		return new BooleanType();
	}

	@Override
	public boolean hasArguments() {
		return true;
	}

	@Override
	public boolean hasParenthesesIfNoArguments() {
		return false;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String render(Type type, List args, SessionFactoryImplementor factory) throws QueryException {

		if (args == null || args.size() != 2) {
			throw new IllegalArgumentException("The function must be passed 2 arguments");
		}

		String field = (String) args.get(0);
		String searchString = (String) args.get(1);
		return field + " @@ to_tsquery(" + searchString + ")";
	}
}