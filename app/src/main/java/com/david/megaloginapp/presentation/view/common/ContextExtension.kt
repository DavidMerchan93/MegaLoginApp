package com.david.megaloginapp.presentation.view.common

import android.content.Context
import android.net.Uri
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.david.megaloginapp.R

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
fun Context.buildExoplayer(uri: Uri) = ExoPlayer.Builder(this).build().apply {
    setMediaItem(MediaItem.fromUri(uri))
    repeatMode = Player.REPEAT_MODE_ALL
    playWhenReady = true
    videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
    prepare()
}

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
fun Context.buildPlayerView(exoPlayer: ExoPlayer) = PlayerView(this).apply {
    hideController()
    useController = false
    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
    player = exoPlayer
    layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
}

fun Context.getVideoUri(): Uri {
    val videoUri = "android.resource://${this.packageName}/${R.raw.background_video_space}"
    return Uri.parse(videoUri)
}
