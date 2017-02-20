package nl.ipo.cds.etl.theme.vrn;

import java.util.ArrayList;
import java.util.List;

import nl.idgis.commons.jobexecutor.JobLogger.LogLevel;
import nl.ipo.cds.etl.ValidatorMessageKey;
import nl.ipo.cds.validation.AttributeExpression;
import nl.ipo.cds.validation.Expression;

public enum Message implements ValidatorMessageKey<Message, Context> {
	ATTRIBUTE_CODE_INVALID, 
	ATTRIBUTE_CODE_CODESPACE_INVALID, 
	ATTRIBUTE_EMPTY,
	ATTRIBUTE_NULL, 
	GEOMETRY_ONLY_SURFACE, 
	GEOMETRY_INTERIOR_RING_OUTSIDE_EXTERIOR, 
	GEOMETRY_INTERIOR_RINGS_WITHIN, 
	GEOMETRY_INTERIOR_DISCONNECTED, 
	GEOMETRY_SELF_INTERSECTION, 
	GEOMETRY_RING_SELF_INTERSECTION, 
	GEOMETRY_RING_NOT_CLOSED, 
	GEOMETRY_DISCONTINUITY,
	GEOMETRY_POINT_DUPLICATION,
	GEOMETRY_SRS_NULL, 
	GEOMETRY_SRS_NOT_RD,
	GEOMETRY_OUTSIDE_BRONHOUDER_AREA,
	GEOMETRY_INVALID_COORDINATES,
	OVERLAP_DETECTION_FAILED,
	OVERLAP_DETECTED,
	HAS_MORE_ERRORS(LogLevel.WARNING);

	@SuppressWarnings("unused")
	private final String[] params;

	private final LogLevel logLevel;

	private final int maxMessageLog;

	private final boolean addToShapeFile;

	private Message(LogLevel logLevel, Integer maxMessageLog, boolean addToShapeFile, String... params) {
		this.maxMessageLog = maxMessageLog == null ? 10 : maxMessageLog;
		this.logLevel = logLevel == null ? LogLevel.ERROR : logLevel;
		this.addToShapeFile = addToShapeFile;
		this.params = params;
	}

	private Message(LogLevel logLevel, Integer maxMessageLog, String... params) {
		this(logLevel, maxMessageLog, false, params);
	}

	private Message(Integer maxMessageLog, boolean addToShapeFile, String... params) {
		this(null, maxMessageLog, addToShapeFile, params);
	}

	private Message(LogLevel logLevel) {
		this(logLevel, null, false);
	}

	private Message(String... params) {
		this(null, null, false, params);
	}

	@Override
	public boolean isBlocking() {
		return getLogLevel ().equals (LogLevel.ERROR);
	}

	@Override
	public List<Expression<Message, Context, ?>> getMessageParameters () {
		final List<Expression<Message, Context, ?>> params = new ArrayList<> ();
		// why twice???
		params.add (new AttributeExpression<Message, Context, String> ("id", String.class));
		params.add (new AttributeExpression<Message, Context, String> ("id", String.class));
		params.add (new AttributeExpression<Message, Context, String> ("identificatie", String.class));
		return params;
	}

	@Override
	public int getMaxMessageLog() {
		return maxMessageLog;
	}

	@Override
	public boolean isAddToShapeFile () {
		return addToShapeFile;
	}

	@Override
	public LogLevel getLogLevel() {
		return logLevel;
	}

	@Override
	public Message getMaxMessageKey() {
		return HAS_MORE_ERRORS;
	}

}
