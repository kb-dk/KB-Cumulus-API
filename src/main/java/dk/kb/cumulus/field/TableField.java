package dk.kb.cumulus.field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.canto.cumulus.FieldDefinition;
import com.canto.cumulus.Item;
import com.canto.cumulus.ItemCollection;

import dk.kb.cumulus.FieldExtractor;

/**
 * A Cumulus Table.
 */
public class TableField extends Field{
    /** The rows of the table.*/
    protected final List<Row> rows;
    
    /**
     * Constructor.
     * @param fieldDefinition The definition of the field.
     * @param fieldType The type of field.
     * @param itemCollection The item collection of the contructor.
     * @param fe The field extractor.
     */
    public TableField(FieldDefinition fieldDefinition, String fieldType, ItemCollection itemCollection, 
            FieldExtractor fe) {
        super(fieldDefinition, fieldType);
        this.rows = new ArrayList<Row>();
        FieldExtractor extractor = new FieldExtractor(itemCollection.getLayout(), fe.getServer(), fe.getCatalog());
        for(Item i : itemCollection) {
            rows.add(new Row(extractor.getMap(i)));
        }
    }
    
    /**
     * @return The rows of the table.
     */
    public List<Row> getRows() {
        return rows;
    }

    @Override
    public boolean isEmpty() {
        return rows.isEmpty();
    };

    /**
     * Class for containing the rows of the table.
     */
    public static class Row {
        /** The map of the elements.*/
        Map<String, String> elements;
        
        /**
         * Constructor.
         * @param coloumns The mapping between the coloumn names and the value in the row.
         */
        public Row(Map<String, String> coloumns) {
            this.elements = new HashMap<String, String>(coloumns);
        }
        
        /**
         * @return The map of the elements.
         */
        public Map<String, String> getElements() {
            return elements;
        }
    }
}
