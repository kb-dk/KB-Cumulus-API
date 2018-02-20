package dk.kb.cumulus;

import java.util.Iterator;

import com.canto.cumulus.Item;
import com.canto.cumulus.RecordItemCollection;

/**
 * Class for encapsulating the result set of a Cumulus extraction.
 */
public class CumulusRecordCollection implements Iterable<CumulusRecord> {
    /** The field extractor for this result set.*/
    protected final FieldExtractor extractor;
    /** Cumulus item collection.*/
    protected final RecordItemCollection itemCollection;
    
    /**
     * Constructor.
     * @param itemCollection The Cumulus items.
     * @param server The CumulusServer.
     * @param catalog The name of the catalog.
     */
    public CumulusRecordCollection(RecordItemCollection itemCollection, CumulusServer server, String catalog) {
        this.extractor = new FieldExtractor(itemCollection.getLayout(), server, catalog);
        this.itemCollection = itemCollection;
    }
    
    @Override
    public Iterator<CumulusRecord> iterator() {
        final Iterator<Item> items = itemCollection.iterator();
        final Iterator<CumulusRecord> res = new Iterator<CumulusRecord>() {

            @Override
            public boolean hasNext() {
                return items.hasNext();
            }

            @Override
            public CumulusRecord next() {
                return new CumulusRecord(extractor, items.next());
            }
        };
        return res;
    }
    
    /**
     * @return The number of items.
     */
    public int getCount() {
        return itemCollection.getItemCount();
    }
    
    /**
     * @return The FieldExtractor the this collection of CumulusRecords.
     */
    public FieldExtractor getFieldExtractor() {
        return extractor;
    }
    
    /**
     * @return The item iterator for the 
     */
    public Iterable<Item> getCumulusItems() {
        return itemCollection;
    }
}
