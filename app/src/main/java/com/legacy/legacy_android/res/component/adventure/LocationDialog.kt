package com.legacy.legacy_android.res.component.adventure

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationDialog(
    permissionState: MultiplePermissionsState,
    showPermissionDialog: (Boolean) -> Unit
) {
    val dialogText = when {
        permissionState.shouldShowRationale -> {
            "Legacy 앱의 모든 기능을 사용하려면 위치 권한이 필요합니다. " +
                    "주변 유적지와 블록을 확인하고 퀘스트를 진행하기 위해 위치 정보를 사용합니다."
        }
        permissionState.revokedPermissions.isNotEmpty() -> {
            "위치 권한이 거부되었습니다. 앱 설정에서 위치 권한을 직접 허용해주세요."
        }
        else -> {
            "Legacy를 즐기려면 위치 권한이 필요합니다!"
        }
    }

    val confirmButtonText = when {
        permissionState.shouldShowRationale -> "권한 허용"
        permissionState.revokedPermissions.isNotEmpty() -> "설정으로 이동"
        else -> "확인"
    }

    AlertDialog(
        onDismissRequest = { showPermissionDialog(false) },
        title = {
            Text(text = "위치 정보 권한 요청")
        },
        text = {
            Text(text = dialogText)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    showPermissionDialog(false)
                    if (permissionState.shouldShowRationale || permissionState.revokedPermissions.isNotEmpty()) {
                        permissionState.launchMultiplePermissionRequest()
                    }
                }
            ) {
                Text(text = confirmButtonText)
            }
        },
        dismissButton = {
            TextButton(onClick = { showPermissionDialog(false) }) {
                Text(text = "취소")
            }
        }
    )
}