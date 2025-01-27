package com.example.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FABView() {
    var expanded by remember { mutableStateOf(false) } // ミニFABの展開状態

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp), // 全体の余白（右下の配置調整用）
        contentAlignment = Alignment.BottomEnd // 右下に配置
    ) {
        // メインFAB
        FloatingActionButton(
            onClick = { expanded = !expanded }, // 展開状態を切り替える
            containerColor = MaterialTheme.colorScheme.primary, // 背景色
            contentColor = Color.White // アイコンの色
        ) {
            Icon(
                imageVector = if (expanded) Icons.Default.Close else Icons.Default.Add,
                contentDescription = "Toggle"
            )
        }

        // ミニFABのリスト
        if (expanded) {
            Column(
                horizontalAlignment = Alignment.End, // FABの右揃え
                verticalArrangement = Arrangement.spacedBy(16.dp), // FAB間の余白
                modifier = Modifier.padding(bottom = 72.dp) // メインFABとの距離調整
            ) {
                MiniFab(
                    icon = Icons.Outlined.Edit,
                    label = "Create Edit",
                    onClick = { /* Handle Create Event */ }
                )
                MiniFab(
                    icon = Icons.Outlined.Menu,
                    label = "Create Menu",
                    onClick = { /* Handle Create Task */ }
                )
            }
        }
    }
}

// ミニFAB（小型ボタンとラベルの組み合わせ）
@Composable
fun MiniFab(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically, // アイコンとテキストを中央揃え
        modifier = Modifier.clickable(onClick = onClick) // クリックイベントを設定
    ) {
        // ラベル
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 8.dp, vertical = 4.dp) // ラベルの余白
        )

        Spacer(modifier = Modifier.width(8.dp)) // アイコンとラベルの間のスペース

        // ミニFABアイコン
        FloatingActionButton(
            onClick = onClick,
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = Color.White,
            modifier = Modifier.size(48.dp) // サイズを小型化
        ) {
            Icon(imageVector = icon, contentDescription = label)
        }
    }
}

