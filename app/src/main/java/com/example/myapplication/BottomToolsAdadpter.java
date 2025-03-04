package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import easynotes.notes.notepad.notebook.privatenotes.note.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import notes.easy.android.mynotes.App;
import notes.easy.android.mynotes.EasyNoteManager;
import notes.easy.android.mynotes.utils.ScreenUtils;
import notes.easy.android.mynotes.view.ToolItem;
import notes.easy.android.mynotes.view.WaveView;

/* compiled from: BottomToolsAdadpter.kt */
/* loaded from: classes4.dex */
public final class BottomToolsAdadpter extends RecyclerView.Adapter<BottomToolsAdadpter.BottomViewHolder> {
    private OnItemClickListener actionListener;
    private int currentRedId = -1;
    private int currentWaveId = -1;
    private List<ToolItem> dataList = new ArrayList();
    private boolean isDark;
    private boolean isR2L;
    private boolean isV1;

    /* compiled from: BottomToolsAdadpter.kt */
    public static final class BottomViewHolder extends RecyclerView.ViewHolder {
        private ImageView actionIcon;
        private View actionItem;
        private View redPoint;
        private WaveView waveView;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public BottomViewHolder(View itemView) {
            super(itemView);
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            View findViewById = itemView.findViewById(R.id.action_item);
            Intrinsics.checkNotNullExpressionValue(findViewById, "itemView.findViewById(R.id.action_item)");
            this.actionItem = findViewById;
            View findViewById2 = itemView.findViewById(R.id.action_point);
            Intrinsics.checkNotNullExpressionValue(findViewById2, "itemView.findViewById(R.id.action_point)");
            this.redPoint = findViewById2;
            View findViewById3 = itemView.findViewById(R.id.action_icon);
            Intrinsics.checkNotNullExpressionValue(findViewById3, "itemView.findViewById(R.id.action_icon)");
            this.actionIcon = (ImageView) findViewById3;
            View findViewById4 = itemView.findViewById(R.id.drawwave_view);
            Intrinsics.checkNotNullExpressionValue(findViewById4, "itemView.findViewById(R.id.drawwave_view)");
            this.waveView = (WaveView) findViewById4;
        }

        public final ImageView getActionIcon() {
            return this.actionIcon;
        }

        public final View getActionItem() {
            return this.actionItem;
        }

        public final View getRedPoint() {
            return this.redPoint;
        }

        public final WaveView getWaveView() {
            return this.waveView;
        }

        public final void setActionIcon(ImageView imageView) {
            Intrinsics.checkNotNullParameter(imageView, "<set-?>");
            this.actionIcon = imageView;
        }

        public final void setActionItem(View view) {
            Intrinsics.checkNotNullParameter(view, "<set-?>");
            this.actionItem = view;
        }

        public final void setRedPoint(View view) {
            Intrinsics.checkNotNullParameter(view, "<set-?>");
            this.redPoint = view;
        }

        public final void setWaveView(WaveView waveView) {
            Intrinsics.checkNotNullParameter(waveView, "<set-?>");
            this.waveView = waveView;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int i6);
    }

    public BottomToolsAdadpter() {
        throw new UnsupportedOperationException("Method not decompiled: notes.easy.android.mynotes.models.adapters.BottomToolsAdadpter.<init>():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final void onBindViewHolder$lambda$0(BottomToolsAdadpter this$0, Ref$ObjectRef toolItem, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(toolItem, "$toolItem");
        OnItemClickListener onItemClickListener = this$0.actionListener;
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(((ToolItem) toolItem.element).getId());
        }
        if (this$0.currentRedId == ((ToolItem) toolItem.element).getId()) {
            this$0.currentRedId = -1;
            this$0.notifyDataSetChanged();
        }
        if (this$0.currentWaveId == ((ToolItem) toolItem.element).getId()) {
            this$0.currentWaveId = -1;
            this$0.notifyDataSetChanged();
        }
    }

    public final int getCurrentRedId() {
        return this.currentRedId;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.dataList.size();
    }

    public final int getPositionById(int i6) {
        Iterator<ToolItem> it2 = this.dataList.iterator();
        int i7 = 0;
        while (it2.hasNext()) {
            int i8 = i7 + 1;
            if (it2.next().getId() == i6) {
                return i7;
            }
            i7 = i8;
        }
        return -1;
    }

    public final void initData() {
        this.dataList.clear();
        for (Integer num : EasyNoteManager.getInstance().getEditingToolList()) {
            if (num != null && num.intValue() == 101) {
                this.dataList.add(new ToolItem(101, R.drawable.ic_font_svg));
            } else if (num != null && num.intValue() == 102) {
                this.dataList.add(new ToolItem(102, R.drawable.ic_checklist_svg));
            } else if (num != null && num.intValue() == 103) {
                this.dataList.add(new ToolItem(103, R.drawable.ic_voice_svg));
            } else if (num != null && num.intValue() == 104) {
                this.dataList.add(new ToolItem(104, R.drawable.ic_draw_black));
            } else if (num != null && num.intValue() == 105) {
                this.dataList.add(new ToolItem(105, R.drawable.ic_add_pic_black));
            } else if (num != null && num.intValue() == 106) {
                this.dataList.add(new ToolItem(106, R.drawable.ic_bottom_emoji));
            } else if (num != null && num.intValue() == 107) {
                this.dataList.add(new ToolItem(107, R.drawable.ic_change_theme));
            } else if (num != null && num.intValue() == 108) {
                this.dataList.add(new ToolItem(108, R.drawable.ic_bullet_svg));
            } else if (num != null && num.intValue() == 109) {
                this.dataList.add(new ToolItem(109, R.drawable.ic_number_svg));
            } else if (num != null && num.intValue() == 110 && this.isV1) {
                this.dataList.add(new ToolItem(110, R.drawable.ic_affix_svg));
            }
        }
        notifyDataSetChanged();
    }

    public final void setIsV1(boolean z6) {
        this.isV1 = z6;
        initData();
    }

    public final void setItemClickListener(OnItemClickListener onItemClickListener) {
        this.actionListener = onItemClickListener;
    }

    public final void updateCurrentToolRed(int i6) {
        this.currentRedId = i6;
        notifyDataSetChanged();
    }

    public final void updateWaveState(int i6) {
        this.currentWaveId = i6;
        notifyDataSetChanged();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [T, java.lang.Object] */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(BottomViewHolder holder, int i6) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        ref$ObjectRef.element = this.dataList.get(i6);
        holder.getActionIcon().setImageResource(((ToolItem) ref$ObjectRef.element).getDrawableId());
        if (i6 == 0) {
            if (this.isR2L) {
                holder.getActionItem().setBackgroundResource(R.drawable.shape_round_corner_end_l2r);
            } else {
                holder.getActionItem().setBackgroundResource(R.drawable.shape_round_corner_start_l2r);
            }
        } else if (i6 != this.dataList.size() - 1) {
            holder.getActionItem().setBackgroundResource(R.drawable.shape_round_normal_layerlist);
        } else if (this.isR2L) {
            holder.getActionItem().setBackgroundResource(R.drawable.shape_round_corner_start_l2r);
        } else {
            holder.getActionItem().setBackgroundResource(R.drawable.shape_round_corner_end_l2r);
        }
        if (this.currentRedId == ((ToolItem) ref$ObjectRef.element).getId()) {
            holder.getRedPoint().setVisibility(0);
        } else {
            holder.getRedPoint().setVisibility(8);
        }
        if (ScreenUtils.isPad(holder.getWaveView().getContext())) {
            holder.getWaveView().setSpace(20);
            holder.getWaveView().setWidth(40);
            holder.getWaveView().setMaxWaveCount(2);
        }
        if (this.isDark) {
            holder.getWaveView().setCenterColor(-1);
        } else {
            holder.getWaveView().setCenterColor(ContextCompat.getColor(App.getAppContext(), R.color.red_ripple));
        }
        if (this.currentWaveId == ((ToolItem) ref$ObjectRef.element).getId()) {
            holder.getWaveView().setVisibility(0);
            holder.getWaveView().startAnim();
        } else {
            holder.getWaveView().setVisibility(8);
            holder.getWaveView().pauseAnim();
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: notes.easy.android.mynotes.models.adapters.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BottomToolsAdadpter.onBindViewHolder$lambda$0(BottomToolsAdadpter.this, ref$ObjectRef, view);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public BottomViewHolder onCreateViewHolder(ViewGroup parent, int i6) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.tools_item, parent, false);
        Intrinsics.checkNotNullExpressionValue(inflate, "from(parent.context).inf…ools_item, parent, false)");
        return new BottomViewHolder(inflate);
    }
}
