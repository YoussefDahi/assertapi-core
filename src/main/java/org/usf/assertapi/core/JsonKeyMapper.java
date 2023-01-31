package org.usf.assertapi.core;

import static com.jayway.jsonpath.JsonPath.compile;
import static org.usf.assertapi.core.DataTransformer.TransformerType.JSON_KEY_MAPPER;
import static org.usf.assertapi.core.Utils.requireNonEmpty;

import java.util.Map;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import lombok.Getter;

/**
 * 
 * @author u$f
 * @since 1.0
 *
 */
@Getter
public final class JsonKeyMapper extends DataTransformer<DocumentContext, DocumentContext> {

	private final JsonPath path; //to object
	private final Map<String, String> map;
	
	public JsonKeyMapper(ReleaseTarget[] applyOn, String path, Map<String, String> map) {
		super(applyOn);
		this.path = compile(requireNonEmpty(path, getType(), "path"));
		this.map = requireNonEmpty(map, getType(), "Map<oldKey,newKey>");
	}
	
	@Override
	protected DocumentContext transform(DocumentContext json) {
		map.entrySet().forEach(e-> json.renameKey(path, e.getKey(), e.getValue())); //require string value
		return json;
    }
	
	@Override
	public String getType() {
		return JSON_KEY_MAPPER.name();
	}
}
