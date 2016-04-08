package to.marcus.rxtesting.ui.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 * marcus - Using this custom adapter to provide sections for Word objects
 */
public class SectionedGridRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = SectionedGridRecyclerViewAdapter.class.getSimpleName();
    private final Context mContext;
    private static final int SECTION_TYPE = 0;
    private boolean mValid = true;
    private int mSectionResourceId;
    private int mTextResourceId;
    private RecyclerView.Adapter mWordRecyclerAdapter;
    private SparseArray<Section> mSections = new SparseArray<>();
    private RecyclerView mRecyclerView;


    public SectionedGridRecyclerViewAdapter(Context context, int sectionResourceId, int textResourceId,RecyclerView recyclerView,
                                            RecyclerView.Adapter baseAdapter) {
        mSectionResourceId = sectionResourceId;
        mTextResourceId = textResourceId;
        mWordRecyclerAdapter = baseAdapter;
        mContext = context;
        mRecyclerView = recyclerView;

        mWordRecyclerAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                mValid = mWordRecyclerAdapter.getItemCount() > 0;
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                mValid = mWordRecyclerAdapter.getItemCount() > 0;
                notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                mValid = mWordRecyclerAdapter.getItemCount() > 0;
                notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                mValid = mWordRecyclerAdapter.getItemCount() > 0;
                notifyItemRangeRemoved(positionStart, itemCount);
            }
        });

        final GridLayoutManager layoutManager = (GridLayoutManager)(mRecyclerView.getLayoutManager());
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (isSectionHeaderPosition(position))? layoutManager.getSpanCount() : 1 ;
            }
        });
    }

    public static class SectionViewHolder extends RecyclerView.ViewHolder {

        public TextView title;

        public SectionViewHolder(View view,int mTextResourceid) {
            super(view);
            title = (TextView) view.findViewById(mTextResourceid);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int typeView) {
        if (typeView == SECTION_TYPE) {
            final View view = LayoutInflater.from(mContext).inflate(mSectionResourceId, parent, false);
            return new SectionViewHolder(view,mTextResourceId);
        }else{
            return mWordRecyclerAdapter.onCreateViewHolder(parent, typeView -1);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder sectionViewHolder, int position) {
        if (isSectionHeaderPosition(position)){
            ((SectionViewHolder)sectionViewHolder).title.setText(mSections.get(position).title);
        }else{
            mWordRecyclerAdapter.onBindViewHolder(sectionViewHolder, sectionedPositionToPosition(position));
        }
    }

    @Override
    public int getItemViewType(int position){
        return isSectionHeaderPosition(position)
                ? SECTION_TYPE
                : mWordRecyclerAdapter.getItemViewType(sectionedPositionToPosition(position))+1;
    }


    public static class Section {
        int firstPosition;
        int sectionedPosition;
        CharSequence title;

        public Section(int firstPosition, CharSequence title) {
            this.firstPosition = firstPosition;
            this.title = title;
        }

        public CharSequence getTitle() {
            return title;
        }
    }

    public void setSections(Section[] sections) {
        mSections.clear();

        Arrays.sort(sections, new Comparator<Section>() {
            @Override
            public int compare(Section o, Section o1) {
                return (o.firstPosition == o1.firstPosition)
                        ? 0
                        : ((o.firstPosition < o1.firstPosition) ? -1 : 1);
            }
        });

        int offset = 0; // offset positions for the headers we're adding
        for (Section section : sections) {
            section.sectionedPosition = section.firstPosition + offset;
            mSections.append(section.sectionedPosition, section);
            ++offset;
        }

        notifyDataSetChanged();
    }

    // Marcus.P - Added method to toggle sections between NavDrawer selections
    public void removeSections(){
        mSections.clear();
        notifyDataSetChanged();
    }

    public int positionToSectionedPosition(int position) {
        int offset = 0;
        for (int i = 0; i < mSections.size(); i++) {
            if (mSections.valueAt(i).firstPosition > position) {
                break;
            }
            ++offset;
        }
        return position + offset;
    }

    public int sectionedPositionToPosition(int sectionedPosition) {
        if (isSectionHeaderPosition(sectionedPosition)) {
            return RecyclerView.NO_POSITION;
        }

        int offset = 0;
        for (int i = 0; i < mSections.size(); i++) {
            if (mSections.valueAt(i).sectionedPosition > sectionedPosition) {
                break;
            }
            --offset;
        }
        return sectionedPosition + offset;
    }

    public boolean isSectionHeaderPosition(int position) {
        return mSections.get(position) != null;
    }

    @Override
    public long getItemId(int position) {
        return isSectionHeaderPosition(position)
                ? Integer.MAX_VALUE - mSections.indexOfKey(position)
                : mWordRecyclerAdapter.getItemId(sectionedPositionToPosition(position));
    }

    @Override
    public int getItemCount() {
        return (mValid ? mWordRecyclerAdapter.getItemCount() + mSections.size() : 0);
    }

    /*
    Marcus.P - Added to trigger WordRecyclerAdapter.onViewDetachedFromWindow
     */
    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder){
        super.onViewDetachedFromWindow(holder);
        if(getItemViewType(holder.getLayoutPosition()) == 1){
            if(!isSectionHeaderPosition(holder.getLayoutPosition())){
                try{
                    mWordRecyclerAdapter.onViewDetachedFromWindow(holder);
                }catch (ClassCastException e){
                    Log.i(TAG, "wrong type");
                }
            }
        }
    }
}