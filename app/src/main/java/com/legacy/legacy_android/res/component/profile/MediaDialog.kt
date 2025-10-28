package com.legacy.legacy_android.res.component.profile

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MediaDialog(
    permissionState: MultiplePermissionsState,
    showPermissionDialog: (Boolean) -> Unit
) {
    val dialogText = when {
        permissionState.shouldShowRationale -> {
            "Legacy 앱의 프로필 변경 기능을 사용하려면 사진 권한이 필요합니다."
        }
        permissionState.revokedPermissions.isNotEmpty() -> {
            "미디어 권한이 거부되었습니다. 앱 설정에서 미디어 권한을 직접 허용해주세요."
        }
        else -> {
            "프로필 변경을 위해서는 사진 권한이 필요합니다!"
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
            Text(text = "사진 정보 권한 요청")
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