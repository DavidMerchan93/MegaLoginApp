package com.david.megaloginapp.presentation.view.common

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.david.megaloginapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ModalBottomDialog(
    @RawRes iconAnimated: Int? = null,
    @StringRes title: Int?,
    @StringRes detail: Int,
    @StringRes buttonTitle: Int,
    isError: Boolean,
    buttonAction: () -> Unit,
    content: @Composable (
        coroutineScope: CoroutineScope,
        modalSheetState: ModalBottomSheetState,
    ) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = false,
    )

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
        sheetContent = {
            Column(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_24dp)),
                verticalArrangement = Arrangement.spacedBy(
                    space = dimensionResource(id = R.dimen.dimen_16dp),
                    alignment = Alignment.CenterVertically,
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (iconAnimated != null) {
                    ViewAnimation(
                        animationFile = iconAnimated,
                        width = R.dimen.dimen_40dp,
                        height = R.dimen.dimen_40dp,
                    )
                }
                if (title != null) {
                    Text(
                        text = stringResource(id = title),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                    )
                }
                Text(text = stringResource(id = detail), fontSize = 18.sp)
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        if (isError) {
                            coroutineScope.launch {
                                modalSheetState.hide()
                            }
                        } else {
                            buttonAction()
                        }
                    },
                ) {
                    Text(
                        text = if (isError) {
                            stringResource(id = R.string.button_ok)
                        } else {
                            stringResource(id = buttonTitle)
                        },
                    )
                }
            }
        },
    ) {
        content(coroutineScope, modalSheetState)
    }
}
