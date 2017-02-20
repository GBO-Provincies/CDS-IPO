package nl.ipo.cds.etl.theme.buisleidingen;

import java.math.BigDecimal;
import java.util.Map;

import nl.ipo.cds.domain.EtlJob;
import nl.ipo.cds.etl.AbstractValidator;
import nl.ipo.cds.validation.AttributeExpression;
import nl.ipo.cds.validation.ValidationReporter;
import nl.ipo.cds.validation.Validator;
import nl.ipo.cds.validation.callbacks.BinaryCallback;
import nl.ipo.cds.validation.callbacks.UnaryCallback;
import nl.ipo.cds.validation.constants.Constant;
import nl.ipo.cds.validation.execute.CompilerException;
import nl.ipo.cds.validation.gml.codelists.CodeListFactory;

import org.deegree.geometry.Geometry;
import org.deegree.geometry.standard.AbstractDefaultGeometry;

import com.vividsolutions.jts.geom.Coordinate;

public abstract class AbstractBuisleidingenValidator<T extends AbstractBuisleidingenFeature> extends AbstractValidator<T, Message, Context> {

	public AbstractBuisleidingenValidator(final Class<T> featureClass, final Map<Object, Object> validatorMessages) throws CompilerException {
		super (Context.class, featureClass, validatorMessages);
	}

	@Override
	public Context beforeJob (final EtlJob job, final CodeListFactory codeListFactory, final ValidationReporter<Message, Context> reporter) {
		return new Context (codeListFactory, reporter);
	}
	
	
	// =========================================================================
	// Validation rules:
	// =========================================================================
	private final AttributeExpression<Message, Context, String> transportrouteId = stringAttr ("transportrouteId");
	private final Constant<Message, Context, Integer> transportrouteIdLength = constant (30);
	
	public Validator<Message, Context> getTransportrouteIdValidator () {
		return validate (
				and (
					validate (not (stringAttr ("transportrouteId").isNull ())).message (Message.TRANSPORTROUTE_ID_NULL),
					validate (not (isBlank (stringAttr ("transportrouteId")))).message (Message.TRANSPORTROUTE_ID_EMPTY),
					validate (lte (length (transportrouteId), transportrouteIdLength)).message (Message.TRANSPORTROUTE_ID_TOO_LONG, transportrouteIdLength)
				).shortCircuit ()
			);
	}
	
	// =========================================================================
	// Callbacks:
	// =========================================================================
	protected static final UnaryCallback<Message, Context, Boolean, String> isUniqueKeyCallback = new UnaryCallback<Message, Context, Boolean, String> () {
		@Override
		public Boolean call (final String input, final Context context) throws Exception {
			if (context.hasId (input)) {
				return false;
			}
			
			context.addId (input);
			
			return true;
		}
	};
	
	protected static final UnaryCallback<Message, Context, Boolean, Double> isLiggingValidCallback = new UnaryCallback<Message, Context, Boolean, Double> () {
		@Override
		public Boolean call (final Double input, final Context context) throws Exception {
			if(input != null){
				if (input > 9999999999.0) {
					return false;
				}
			}
			return true;
		}
	};
	
	protected static final BinaryCallback<Message, Context, Boolean, String, String> isExternalIdValidCallback = new BinaryCallback<Message, Context, Boolean, String, String> () {
		@Override
		public Boolean call (final String a, final String b, final Context context) throws Exception {
			if ((a + "#" + b).length() > 30) {
				return false;
			}
			return true;
		}
	};
	
	protected static final BinaryCallback<Message, Context, String, String, String> getExternalIdCallback = new BinaryCallback<Message, Context, String, String, String> () {
		@Override
		public String call (final String a, final String b, final Context context) throws Exception {
			return a + "#" + b;
		}
	};
	
	protected static final BinaryCallback<Message, Context, Boolean, String, String> isUniqueCompoundKeyCallback = new BinaryCallback<Message, Context, Boolean, String, String> () {
		@Override
		public Boolean call (final String a, final String b, final Context context) throws Exception {
			if (context.hasId (a + "|" + b)) {
				return false;
			}
			
			context.addId (a + "|" + b);
			
			return true;
		}
	};
	
	protected static final BinaryCallback<Message, Context, Boolean, String, String> isTransportroutenaamConsistent = new BinaryCallback<Message, Context, Boolean, String, String> () {
		@Override
		public Boolean call (final String id, final String name, final Context context) throws Exception {
			final String currentName = context.getTransportrouteNaam (id);
			if (currentName != null && !currentName.equals (name)) {
				return false;
			}
			
			context.addTransportrouteNaam (id, name);
			
			return true;
		}
	};
	
	protected static final UnaryCallback<Message, Context, Boolean, Geometry> isConsecutiveDuplicateCoordinatesCallback = new UnaryCallback<Message, Context, Boolean, Geometry> () {
		@Override
		public Boolean call (final Geometry geom,  final Context context) throws Exception {
			AbstractDefaultGeometry abstractGeometrie = (AbstractDefaultGeometry)geom;
			com.vividsolutions.jts.geom.Geometry jtGeometrie = abstractGeometrie.getJTSGeometry();
			Coordinate[] cordinates = jtGeometrie.getCoordinates();
			for (int i=0; i<cordinates.length; i++) {
				if(i != cordinates.length-1){
					Coordinate first = cordinates[i];
					Double x = new BigDecimal(first.x).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
					Double y = new BigDecimal(first.y).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
					first.x = x;
					first.y = y;
					Coordinate next = cordinates[i+1];
					x = new BigDecimal(next.x).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
					y = new BigDecimal(next.y).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
					next.x = x;
					next.y = y;
					int equals = first.compareTo(next);
					if(equals == 0){
						return false;
					}
				}
			}
			return true;
		}
	};
	

	
	protected static final UnaryCallback<Message, Context, String, Geometry> getConsecutiveDuplicateCoordinatesCallback = new UnaryCallback<Message, Context, String, Geometry> () {
		@Override
		public String call (final Geometry geom, final Context context) throws Exception {
			String points = "";
			AbstractDefaultGeometry abstractGeometrie = (AbstractDefaultGeometry)geom;
			com.vividsolutions.jts.geom.Geometry jtGeometrie = abstractGeometrie.getJTSGeometry();
			Coordinate[] cordinates = jtGeometrie.getCoordinates();
			for (int i=0; i<cordinates.length; i++) {
				if(i != cordinates.length-1){
					Coordinate first = cordinates[i];
					Double x = new BigDecimal(first.x).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
					Double y = new BigDecimal(first.y).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
					first.x = x;
					first.y = y;
					Coordinate next = cordinates[i+1];
					x = new BigDecimal(next.x).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
					y = new BigDecimal(next.y).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
					next.x = x;
					next.y = y;
					int equals = first.compareTo(next);
					if(equals == 0){
						points = "" + first.x + "," + first.y + ":" + next.x + "," + next.y ;
					}
				}
			}
			return points;
		}		
	};
}
