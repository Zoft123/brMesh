package com.brgd.brblmesh.Main.Fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import cn.com.broadlink.blelight.BLSBleLight;
import com.brgd.brblmesh.GeneralAdapter.MusicAdapter;
import com.brgd.brblmesh.GeneralAdapter.model.ModDiyColor;
import com.brgd.brblmesh.GeneralAdapter.model.Song;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalBluetooth;
import com.brgd.brblmesh.GeneralClass.GlobalToast;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.MyLinearLayoutManager;
import com.brgd.brblmesh.GeneralClass.MyService;
import com.brgd.brblmesh.GeneralClass.SharePreferenceUtils;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.Main.Activity.DeviceCtrlActivity;
import com.brgd.brblmesh.Main.Interface.MusicFragmentListener;
import com.brgd.brblmesh.R;
import io.realm.Realm;
import io.realm.RealmResults;
import j$.util.Objects;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/* JADX INFO: loaded from: classes.dex */
public class MusicFragment extends Fragment implements SeekBar.OnSeekBarChangeListener, MusicFragmentListener {
    private Song currentSong;
    private int currentSongIndex;
    private DeviceCtrlActivity deviceCtrlActivity;
    ImageView downView;
    TextView endTimeView;
    private Intent foregroundIntent;
    public boolean isGetPermission;
    private Timer mTimer;
    private List<Integer> modDiyColorList1;
    private List<Integer> modDiyColorList2;
    private List<Integer> modDiyColorList3;
    private List<Integer> modDiyColorList4;
    private List<Integer> modDiyColorList5;
    private MusicAdapter musicAdapter;
    RecyclerView musicRecyclerView;
    ImageView playView;
    private Realm realm;
    ImageView recyclePlayView;
    int recycleState;
    SeekBar seekBar;
    TextView singerView;
    private List<Song> songList;
    TextView songNameView;
    int songTime;
    TextView startTimeView;
    ImageView upView;
    private Visualizer visualizer;
    public int addr = -1;
    public int groupId = -1;
    public int tempGroupId = -1;
    public int type = -1;
    public boolean hasRequestRead = false;
    MediaPlayer mediaPlayer = new MediaPlayer();
    public int currentThemeIndex = 0;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Fragment.MusicFragment.2
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            if (!MusicFragment.this.isGetPermission) {
                MusicFragment.this.deviceCtrlActivity.musicPermissionDialog();
                return;
            }
            if (MusicFragment.this.songList.isEmpty()) {
                GlobalToast.showText(MusicFragment.this.deviceCtrlActivity.getApplicationContext(), R.string.noMusic, 1);
                return;
            }
            int id = view.getId();
            if (id == R.id.music_play) {
                if (MusicFragment.this.mediaPlayer.isPlaying()) {
                    MusicFragment.this.playerPause();
                    return;
                } else {
                    MusicFragment.this.playerStart();
                    return;
                }
            }
            if (id == R.id.music_recycle) {
                if (MusicFragment.this.recycleState == 1) {
                    MusicFragment.this.recycleState++;
                    MusicFragment.this.recyclePlayView.setImageDrawable(AppCompatResources.getDrawable(MusicFragment.this.deviceCtrlActivity, R.drawable.one_recycle));
                    return;
                } else if (MusicFragment.this.recycleState == 2) {
                    MusicFragment.this.recycleState++;
                    MusicFragment.this.recyclePlayView.setImageDrawable(AppCompatResources.getDrawable(MusicFragment.this.deviceCtrlActivity, R.drawable.random_play));
                    return;
                } else {
                    MusicFragment.this.recycleState = 1;
                    MusicFragment.this.recyclePlayView.setImageDrawable(AppCompatResources.getDrawable(MusicFragment.this.deviceCtrlActivity, R.drawable.all_recycle));
                    return;
                }
            }
            if (id == R.id.music_up || id == R.id.music_down) {
                MusicFragment.this.stopTimer();
                MusicFragment.this.playView.setSelected(false);
                if (id == R.id.music_up) {
                    if (MusicFragment.this.currentSongIndex == 0) {
                        MusicFragment musicFragment = MusicFragment.this;
                        musicFragment.currentSongIndex = musicFragment.songList.size() - 1;
                    } else {
                        MusicFragment.this.currentSongIndex--;
                    }
                } else if (MusicFragment.this.currentSongIndex == MusicFragment.this.songList.size() - 1) {
                    MusicFragment.this.currentSongIndex = 0;
                } else {
                    MusicFragment.this.currentSongIndex++;
                }
                MusicFragment.this.currentSong.isSelect = false;
                MusicFragment musicFragment2 = MusicFragment.this;
                musicFragment2.currentSong = (Song) musicFragment2.songList.get(MusicFragment.this.currentSongIndex);
                MusicFragment.this.currentSong.isSelect = true;
                MusicFragment.this.prepareStartSong();
                MusicFragment.this.setViewValue();
            }
        }
    };
    private final Visualizer.OnDataCaptureListener dataCaptureListener = new Visualizer.OnDataCaptureListener() { // from class: com.brgd.brblmesh.Main.Fragment.MusicFragment.3
        @Override // android.media.audiofx.Visualizer.OnDataCaptureListener
        public void onWaveFormDataCapture(Visualizer visualizer, byte[] bArr, int i) {
        }

        @Override // android.media.audiofx.Visualizer.OnDataCaptureListener
        public void onFftDataCapture(Visualizer visualizer, byte[] bArr, int i) {
            float f = 0.0f;
            int i2 = 0;
            for (int i3 = 0; i3 < bArr.length; i3 += 2) {
                float fHypot = (float) Math.hypot(bArr[i3], bArr[i3 + 1]);
                if (fHypot > f) {
                    i2 = i3;
                    f = fHypot;
                }
            }
            float maxCaptureRate = (i2 * (Visualizer.getMaxCaptureRate() / 2)) / Visualizer.getCaptureSizeRange()[1];
            if (maxCaptureRate > 0.0f) {
                MusicFragment.this.setMusicCtrl(maxCaptureRate);
            }
        }
    };
    private int lightness = 120;
    private long currentTime = 0;

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.deviceCtrlActivity = (DeviceCtrlActivity) context;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_music, viewGroup, false);
        initView(viewInflate);
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (this.deviceCtrlActivity.currentTabPosition == 2) {
            this.deviceCtrlActivity.checkRW();
            refreshDIYData();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        playerStop();
        this.realm.close();
    }

    private void initView(View view) {
        Realm.init(this.deviceCtrlActivity.getApplicationContext());
        this.realm = Realm.getDefaultInstance();
        this.songNameView = (TextView) view.findViewById(R.id.music_songName);
        this.singerView = (TextView) view.findViewById(R.id.music_singerName);
        SeekBar seekBar = (SeekBar) view.findViewById(R.id.music_presentSeekBar);
        this.seekBar = seekBar;
        seekBar.setOnSeekBarChangeListener(this);
        this.startTimeView = (TextView) view.findViewById(R.id.music_starTime);
        this.endTimeView = (TextView) view.findViewById(R.id.music_endTime);
        ImageView imageView = (ImageView) view.findViewById(R.id.music_play);
        this.playView = imageView;
        imageView.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView2 = (ImageView) view.findViewById(R.id.music_up);
        this.upView = imageView2;
        imageView2.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView3 = (ImageView) view.findViewById(R.id.music_down);
        this.downView = imageView3;
        imageView3.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView4 = (ImageView) view.findViewById(R.id.music_recycle);
        this.recyclePlayView = imageView4;
        imageView4.setOnClickListener(this.disDoubleClickListener);
        initSongListView(view);
        this.recycleState = 1;
        this.recyclePlayView.setImageDrawable(AppCompatResources.getDrawable(this.deviceCtrlActivity, R.drawable.all_recycle));
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: com.brgd.brblmesh.Main.Fragment.MusicFragment$$ExternalSyntheticLambda1
                @Override // android.media.MediaPlayer.OnCompletionListener
                public final void onCompletion(MediaPlayer mediaPlayer2) {
                    this.f$0.lambda$initView$0(mediaPlayer2);
                }
            });
            this.mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() { // from class: com.brgd.brblmesh.Main.Fragment.MusicFragment$$ExternalSyntheticLambda2
                @Override // android.media.MediaPlayer.OnErrorListener
                public final boolean onError(MediaPlayer mediaPlayer2, int i, int i2) {
                    return this.f$0.lambda$initView$1(mediaPlayer2, i, i2);
                }
            });
        }
        this.currentThemeIndex = ((Integer) SharePreferenceUtils.get(this.deviceCtrlActivity.getApplicationContext(), GlobalVariable.musicTheme, -1)).intValue();
        this.modDiyColorList1 = new ArrayList();
        this.modDiyColorList2 = new ArrayList();
        this.modDiyColorList3 = new ArrayList();
        this.modDiyColorList4 = new ArrayList();
        this.modDiyColorList5 = new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(MediaPlayer mediaPlayer) {
        this.playView.setSelected(false);
        stopTimer();
        int i = this.recycleState;
        if (i == 1 || i == 3) {
            if (i == 1) {
                if (this.currentSongIndex == this.songList.size() - 1) {
                    this.currentSongIndex = 0;
                } else {
                    this.currentSongIndex++;
                }
            } else {
                this.currentSongIndex = (int) (Math.random() * ((double) this.songList.size()));
            }
            this.currentSong.isSelect = false;
            Song song = this.songList.get(this.currentSongIndex);
            this.currentSong = song;
            song.isSelect = true;
            prepareStartSong();
            setViewValue();
            return;
        }
        playerStart();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initView$1(MediaPlayer mediaPlayer, int i, int i2) {
        playerPause();
        return true;
    }

    private void refreshDIYData() {
        for (int i = 1; i < 6; i++) {
            RealmResults<ModDiyColor> realmResultsFindAll = this.realm.where(ModDiyColor.class).equalTo(GlobalVariable.modNumber, Integer.valueOf(i)).findAll();
            if (i == 1) {
                this.modDiyColorList1.clear();
                for (ModDiyColor modDiyColor : realmResultsFindAll) {
                    if (modDiyColor.getDiyColorR() != -2 && modDiyColor.getDiyColorR() != 0) {
                        this.modDiyColorList1.add(Integer.valueOf(modDiyColor.getDiyColorR()));
                    } else if (modDiyColor.getDiyColor() != -2) {
                        this.modDiyColorList1.add(Integer.valueOf(modDiyColor.getDiyColor()));
                    }
                }
            } else if (i == 2) {
                this.modDiyColorList2.clear();
                for (ModDiyColor modDiyColor2 : realmResultsFindAll) {
                    if (modDiyColor2.getDiyColorR() != -2 && modDiyColor2.getDiyColorR() != 0) {
                        this.modDiyColorList2.add(Integer.valueOf(modDiyColor2.getDiyColorR()));
                    } else if (modDiyColor2.getDiyColor() != -2) {
                        this.modDiyColorList2.add(Integer.valueOf(modDiyColor2.getDiyColor()));
                    }
                }
            } else if (i == 3) {
                this.modDiyColorList3.clear();
                for (ModDiyColor modDiyColor3 : realmResultsFindAll) {
                    if (modDiyColor3.getDiyColorR() != -2 && modDiyColor3.getDiyColorR() != 0) {
                        this.modDiyColorList3.add(Integer.valueOf(modDiyColor3.getDiyColorR()));
                    } else if (modDiyColor3.getDiyColor() != -2) {
                        this.modDiyColorList3.add(Integer.valueOf(modDiyColor3.getDiyColor()));
                    }
                }
            } else if (i == 4) {
                this.modDiyColorList4.clear();
                for (ModDiyColor modDiyColor4 : realmResultsFindAll) {
                    if (modDiyColor4.getDiyColorR() != -2 && modDiyColor4.getDiyColorR() != 0) {
                        this.modDiyColorList4.add(Integer.valueOf(modDiyColor4.getDiyColorR()));
                    } else if (modDiyColor4.getDiyColor() != -2) {
                        this.modDiyColorList4.add(Integer.valueOf(modDiyColor4.getDiyColor()));
                    }
                }
            } else if (i == 5) {
                this.modDiyColorList5.clear();
                for (ModDiyColor modDiyColor5 : realmResultsFindAll) {
                    if (modDiyColor5.getDiyColorR() != -2 && modDiyColor5.getDiyColorR() != 0) {
                        this.modDiyColorList5.add(Integer.valueOf(modDiyColor5.getDiyColorR()));
                    } else if (modDiyColor5.getDiyColor() != -2) {
                        this.modDiyColorList5.add(Integer.valueOf(modDiyColor5.getDiyColor()));
                    }
                }
            }
        }
    }

    private void initSongListView(View view) {
        this.songList = new ArrayList();
        this.musicRecyclerView = (RecyclerView) view.findViewById(R.id.music_list_recyclerView);
        MusicAdapter musicAdapter = new MusicAdapter(this.deviceCtrlActivity, this.songList);
        this.musicAdapter = musicAdapter;
        this.musicRecyclerView.setAdapter(musicAdapter);
        this.musicRecyclerView.setLayoutManager(new MyLinearLayoutManager(this.deviceCtrlActivity));
        this.musicAdapter.setOnClickListener(new MusicAdapter.OnClickListener() { // from class: com.brgd.brblmesh.Main.Fragment.MusicFragment$$ExternalSyntheticLambda4
            @Override // com.brgd.brblmesh.GeneralAdapter.MusicAdapter.OnClickListener
            public final void onClick(View view2, int i) {
                this.f$0.lambda$initSongListView$2(view2, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initSongListView$2(View view, int i) {
        stopTimer();
        this.currentSongIndex = i;
        this.currentSong.isSelect = false;
        Song song = this.songList.get(this.currentSongIndex);
        this.currentSong = song;
        song.isSelect = true;
        prepareStartSong();
        setViewValue();
    }

    private void startTimer() {
        stopTimer();
        Timer timer = new Timer();
        this.mTimer = timer;
        timer.schedule(new TimerTask() { // from class: com.brgd.brblmesh.Main.Fragment.MusicFragment.1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                DeviceCtrlActivity.MyHandler myHandler = MusicFragment.this.deviceCtrlActivity.myHandler;
                DeviceCtrlActivity.MyHandler myHandler2 = MusicFragment.this.deviceCtrlActivity.myHandler;
                Objects.requireNonNull(MusicFragment.this.deviceCtrlActivity);
                myHandler.sendMessage(myHandler2.obtainMessage(101));
            }
        }, 0L, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopTimer() {
        Timer timer = this.mTimer;
        if (timer != null) {
            timer.cancel();
            this.mTimer.purge();
            this.mTimer = null;
        }
    }

    private Map<String, Song> getLocalSong() {
        HashMap map = new HashMap();
        Cursor cursorQuery = this.deviceCtrlActivity.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, "title_key");
        if (cursorQuery != null) {
            while (cursorQuery.moveToNext()) {
                String string = cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("_display_name"));
                if (!TextUtils.isEmpty(string)) {
                    String string2 = cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("artist"));
                    String string3 = cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("_data"));
                    int i = cursorQuery.getInt(cursorQuery.getColumnIndexOrThrow(TypedValues.TransitionType.S_DURATION));
                    long j = cursorQuery.getLong(cursorQuery.getColumnIndexOrThrow("_size"));
                    Song song = new Song();
                    song.setName(string);
                    song.setSinger(string2);
                    song.setPath(string3);
                    song.setDuration(i);
                    song.setSize(j);
                    if (!map.containsKey(song.getName())) {
                        map.put(song.name, song);
                    }
                }
            }
            cursorQuery.close();
        }
        return map;
    }

    private void sortSong() {
        this.songList.clear();
        Map<String, Song> localSong = getLocalSong();
        ArrayList arrayList = new ArrayList(localSong.keySet());
        Collections.sort(arrayList);
        for (int i = 0; i < arrayList.size(); i++) {
            this.songList.add(localSong.get((String) arrayList.get(i)));
        }
    }

    private void refreshSong() {
        sortSong();
        if (this.songList.isEmpty()) {
            return;
        }
        this.currentSongIndex = 0;
        Song song = this.songList.get(0);
        this.currentSong = song;
        song.isSelect = true;
        try {
            this.mediaPlayer.setDataSource(this.currentSong.path);
            this.mediaPlayer.prepareAsync();
        } catch (Exception e) {
            Log.d("printStackTrace", "printStackTrace" + e);
        }
        setViewValue();
        this.musicAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setViewValue() {
        Song song = this.currentSong;
        if (song != null) {
            this.songNameView.setText(song.name);
            this.singerView.setText(this.currentSong.singer);
            this.endTimeView.setText(Tool.formatTime(this.currentSong.duration));
            int i = this.currentSong.duration / 1000;
            this.songTime = i;
            this.seekBar.setMax(i);
            this.seekBar.setProgress(this.mediaPlayer.getCurrentPosition() / 1000);
            this.musicAdapter.notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void prepareStartSong() {
        try {
            releaseVisualizer();
            this.mediaPlayer.reset();
            this.mediaPlayer.setDataSource(this.currentSong.path);
            this.mediaPlayer.prepareAsync();
            this.mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.brgd.brblmesh.Main.Fragment.MusicFragment$$ExternalSyntheticLambda3
                @Override // android.media.MediaPlayer.OnPreparedListener
                public final void onPrepared(MediaPlayer mediaPlayer) {
                    this.f$0.lambda$prepareStartSong$3(mediaPlayer);
                }
            });
        } catch (Exception e) {
            Log.d("printStackTrace", "printStackTrace" + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$prepareStartSong$3(MediaPlayer mediaPlayer) {
        playerStart();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playerStart() {
        if (this.mediaPlayer.isPlaying()) {
            return;
        }
        this.playView.setSelected(true);
        this.mediaPlayer.start();
        startTimer();
        initVisualizer();
        if (this.foregroundIntent == null) {
            this.foregroundIntent = new Intent(this.deviceCtrlActivity, (Class<?>) MyService.class);
            if (Build.VERSION.SDK_INT >= 26) {
                this.deviceCtrlActivity.startForegroundService(this.foregroundIntent);
            } else {
                this.deviceCtrlActivity.startService(this.foregroundIntent);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playerPause() {
        if (this.mediaPlayer.isPlaying()) {
            this.playView.setSelected(false);
            this.mediaPlayer.pause();
            stopTimer();
            releaseVisualizer();
            Intent intent = this.foregroundIntent;
            if (intent != null) {
                this.deviceCtrlActivity.stopService(intent);
                this.foregroundIntent = null;
            }
        }
    }

    private void playerStop() {
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            this.mediaPlayer.release();
            stopTimer();
        }
        releaseVisualizer();
        Intent intent = this.foregroundIntent;
        if (intent != null) {
            this.deviceCtrlActivity.stopService(intent);
            this.foregroundIntent = null;
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        if (this.currentSong != null) {
            this.startTimeView.setText(Tool.formatTime(i * 1000));
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStartTrackingTouch(SeekBar seekBar) {
        if (this.currentSong != null) {
            stopTimer();
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (!this.isGetPermission) {
            this.deviceCtrlActivity.musicPermissionDialog();
        } else if (this.currentSong != null) {
            this.mediaPlayer.seekTo(seekBar.getProgress() * 1000);
            startTimer();
        }
    }

    @Override // com.brgd.brblmesh.Main.Interface.MusicFragmentListener
    public void updateProgress() {
        this.seekBar.setProgress(this.mediaPlayer.getCurrentPosition() / 1000);
    }

    @Override // com.brgd.brblmesh.Main.Interface.MusicFragmentListener
    public void endMusic() {
        playerPause();
    }

    @Override // com.brgd.brblmesh.Main.Interface.MusicFragmentListener
    public void refreshSongList() {
        this.isGetPermission = true;
        if (this.songList.isEmpty()) {
            refreshSong();
        }
    }

    private void initVisualizer() {
        try {
            releaseVisualizer();
            Visualizer visualizer = new Visualizer(this.mediaPlayer.getAudioSessionId());
            this.visualizer = visualizer;
            visualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
            this.visualizer.setDataCaptureListener(this.dataCaptureListener, Visualizer.getMaxCaptureRate() / 2, false, true);
            this.visualizer.setEnabled(true);
        } catch (Exception e) {
            Log.e("", "initVisualizer error: " + e.toString());
        }
    }

    private void releaseVisualizer() {
        Visualizer visualizer = this.visualizer;
        if (visualizer != null) {
            visualizer.setEnabled(false);
            this.visualizer.release();
            this.visualizer = null;
        }
    }

    private void sendMusicCtrlCmd() {
        int randomColor1;
        int i = this.currentThemeIndex;
        if (i == 1) {
            randomColor1 = Tool.getRandomColor1(this.modDiyColorList1);
        } else if (i == 2) {
            randomColor1 = Tool.getRandomColor1(this.modDiyColorList2);
        } else if (i == 3) {
            randomColor1 = Tool.getRandomColor1(this.modDiyColorList3);
        } else if (i == 4) {
            randomColor1 = Tool.getRandomColor1(this.modDiyColorList4);
        } else if (i == 5) {
            randomColor1 = Tool.getRandomColor1(this.modDiyColorList5);
        } else {
            randomColor1 = Tool.getRandomColor();
        }
        if (randomColor1 == -1) {
            randomColor1 = Tool.getRandomColor();
        }
        int iRed = Color.red(randomColor1);
        int iGreen = Color.green(randomColor1);
        int iBlue = Color.blue(randomColor1);
        int i2 = this.addr;
        if (i2 != -1) {
            BLSBleLight.controlWithDevice(i2, GlobalBluetooth.getInstance().rgbCommand(true, this.lightness, iRed, iGreen, iBlue), 100);
            return;
        }
        int i3 = this.tempGroupId;
        if (i3 != -1) {
            BLSBleLight.groupControlWithType(this.type, i3, GlobalBluetooth.getInstance().rgbCommand(true, this.lightness, iRed, iGreen, iBlue), 100);
            return;
        }
        int i4 = this.groupId;
        if (i4 != -1) {
            BLSBleLight.groupControlWithType(this.type, i4, GlobalBluetooth.getInstance().rgbCommand(true, this.lightness, iRed, iGreen, iBlue), 100);
        }
    }

    public void setMusicCtrl(float f) {
        long timeInMillis = Calendar.getInstance().getTimeInMillis();
        if (timeInMillis - this.currentTime < 200) {
            return;
        }
        this.currentTime = timeInMillis;
        if (f > 400.0f) {
            this.lightness = 65;
        } else if (f > 0.0f && f <= 30.0f) {
            this.lightness = 120;
        }
        sendMusicCtrlCmd();
    }
}
