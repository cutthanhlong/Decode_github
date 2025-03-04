package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import easynotes.notes.notepad.notebook.privatenotes.note.R;
import java.util.Collections;
import java.util.List;
import notes.easy.android.mynotes.App;
import notes.easy.android.mynotes.firebase.FirebaseReportUtils;
import notes.easy.android.mynotes.models.Attachment;
import notes.easy.android.mynotes.models.views.SquareImageView;
import notes.easy.android.mynotes.utils.BitmapUtil;
import notes.easy.android.mynotes.utils.ConstantsBase;
import notes.easy.android.mynotes.utils.ScreenUtils;

/* loaded from: classes4.dex */
public class PicGridAdapter extends RecyclerView.Adapter<AttachmentHolder> implements ItemTouchHelperAdapterNew {
    private List<Attachment> attachmentsList;
    private Context mActivity;
    private OnListCallback mListener;

    public static class AttachmentHolder extends RecyclerView.ViewHolder {
        private final SquareImageView image;

        public AttachmentHolder(View view) {
            super(view);
            this.image = (SquareImageView) view.findViewById(R.id.gridview_item_picture);
        }
    }

    public interface OnListCallback {
        void onItemClick(View view, Attachment attachment, int i6);

        void onItemDragFinished(List<Attachment> list, int i6, int i7);

        void onItemLongClick();
    }

    public PicGridAdapter(Context context, List<Attachment> list) {
        this.mActivity = context;
        this.attachmentsList = list == null ? Collections.emptyList() : list;
        setHasStableIds(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(Attachment attachment, int i6, View view) {
        OnListCallback onListCallback = this.mListener;
        if (onListCallback != null) {
            onListCallback.onItemClick(view, attachment, i6);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$onBindViewHolder$1(View view) {
        OnListCallback onListCallback = this.mListener;
        if (onListCallback == null) {
            return true;
        }
        onListCallback.onItemLongClick();
        return true;
    }

    public List<Attachment> getAttachmentsList() {
        return this.attachmentsList;
    }

    public int getCount() {
        return this.attachmentsList.size();
    }

    public Attachment getItem(int i6) {
        if (i6 >= this.attachmentsList.size() || i6 < 0) {
            return null;
        }
        return this.attachmentsList.get(i6);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.attachmentsList.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i6) {
        return this.attachmentsList.get(i6).getGradId();
    }

    @Override // notes.easy.android.mynotes.models.adapters.ItemTouchHelperAdapterNew
    public void onItemClear(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder != null) {
            viewHolder.itemView.setScaleX(1.0f);
            viewHolder.itemView.setScaleY(1.0f);
        }
    }

    @Override // notes.easy.android.mynotes.models.adapters.ItemTouchHelperAdapterNew
    public void onItemDissmiss(RecyclerView.ViewHolder viewHolder) {
        int adapterPosition = viewHolder.getAdapterPosition();
        this.attachmentsList.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
    }

    @Override // notes.easy.android.mynotes.models.adapters.ItemTouchHelperAdapterNew
    public void onItemMove(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
        int adapterPosition = viewHolder.getAdapterPosition();
        int adapterPosition2 = viewHolder2.getAdapterPosition();
        if (adapterPosition >= this.attachmentsList.size() || adapterPosition2 >= this.attachmentsList.size()) {
            return;
        }
        Collections.swap(this.attachmentsList, adapterPosition, adapterPosition2);
        notifyItemMoved(adapterPosition, adapterPosition2);
        OnListCallback onListCallback = this.mListener;
        if (onListCallback != null) {
            onListCallback.onItemDragFinished(this.attachmentsList, adapterPosition, adapterPosition2);
        }
    }

    @Override // notes.easy.android.mynotes.models.adapters.ItemTouchHelperAdapterNew
    public void onItemSelect(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder != null) {
            ScreenUtils.vibrate(this.mActivity, 50L);
            viewHolder.itemView.setScaleX(1.05f);
            viewHolder.itemView.setScaleY(1.05f);
        }
    }

    public void setOnListCallbackListener(OnListCallback onListCallback) {
        this.mListener = onListCallback;
    }

    public void updateGradId() {
        for (int i6 = 0; i6 < this.attachmentsList.size(); i6++) {
            this.attachmentsList.get(i6).setGradId(i6);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(AttachmentHolder attachmentHolder, final int i6) {
        final Attachment attachment = this.attachmentsList.get(i6);
        try {
            if (attachment.getMime_type() != null && (attachment.getMime_type().equals("image/jpeg") || attachment.getMime_type().equals(ConstantsBase.MIME_TYPE_SKETCH))) {
                Glide.with(App.getAppContext()).load(BitmapUtil.getThumbnailUri(this.mActivity, attachment)).apply((BaseRequestOptions<?>) new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(true)).into(attachmentHolder.image);
            }
        } catch (Exception unused) {
            FirebaseReportUtils.getInstance().reportNew("edit_tool_pic_preview_error");
        }
        attachmentHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: notes.easy.android.mynotes.models.adapters.e
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PicGridAdapter.this.lambda$onBindViewHolder$0(attachment, i6, view);
            }
        });
        attachmentHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() { // from class: notes.easy.android.mynotes.models.adapters.f
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                boolean lambda$onBindViewHolder$1;
                lambda$onBindViewHolder$1 = PicGridAdapter.this.lambda$onBindViewHolder$1(view);
                return lambda$onBindViewHolder$1;
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public AttachmentHolder onCreateViewHolder(ViewGroup viewGroup, int i6) {
        return new AttachmentHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gridview_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewRecycled(AttachmentHolder attachmentHolder) {
        super.onViewRecycled((PicGridAdapter) attachmentHolder);
        SquareImageView squareImageView = attachmentHolder.image;
        if (squareImageView != null) {
            Glide.with(this.mActivity).clear(squareImageView);
        }
    }
}
