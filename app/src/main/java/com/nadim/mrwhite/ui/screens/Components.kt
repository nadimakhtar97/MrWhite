package com.nadim.mrwhite.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.nadim.mrwhite.model.Role
import com.nadim.mrwhite.ui.theme.BgSurface
import com.nadim.mrwhite.ui.theme.BorderSubtle
import com.nadim.mrwhite.ui.theme.RoleCivilian
import com.nadim.mrwhite.ui.theme.RoleMrWhite
import com.nadim.mrwhite.ui.theme.RoleUndercover
import com.nadim.mrwhite.ui.theme.StateElimination
import com.nadim.mrwhite.ui.theme.SurfaceContainer
import com.nadim.mrwhite.ui.theme.SurfaceContainerLow
import com.nadim.mrwhite.ui.theme.SurfacePure
import com.nadim.mrwhite.ui.theme.TextMuted
import com.nadim.mrwhite.ui.theme.TextPrimary
import com.nadim.mrwhite.ui.theme.TextSubtle

@Composable
fun TopHeader(
    title: String,
    onBackClick: (() -> Unit)? = null,
    onCloseClick: (() -> Unit)? = null,
    showUserIcon: Boolean = true
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(BgSurface)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (onBackClick != null) {
                IconButton(onClick = onBackClick) {
                    Text(
                        text = "←",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }
            } else if (onCloseClick != null) {
                IconButton(onClick = onCloseClick) {
                    Text(
                        text = "✕",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }
            }
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp),
                color = TextPrimary,
                fontWeight = FontWeight.Bold
            )
        }

        if (showUserIcon) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(TextPrimary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "👤",
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun StepperControl(
    value: Int,
    onValueChange: (Int) -> Unit,
    minValue: Int = 1,
    maxValue: Int = 12
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(if (value > minValue) SurfaceContainer else SurfaceContainerLow)
                .clickable(enabled = value > minValue) { onValueChange(value - 1) },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "−",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = if (value > minValue) TextPrimary else TextSubtle
            )
        }

        Text(
            text = value.toString(),
            fontFamily = FontFamily.Monospace,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )

        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(if (value < maxValue) SurfaceContainer else SurfaceContainerLow)
                .clickable(enabled = value < maxValue) { onValueChange(value + 1) },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "+",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = if (value < maxValue) TextPrimary else TextSubtle
            )
        }
    }
}

@Composable
fun RoleBadge(
    role: Role,
    isEliminated: Boolean = false
) {
    val (bgColor, textColor, label) = when {
        isEliminated -> Triple(StateElimination.copy(alpha = 0.15f), StateElimination, "${role.displayName} · Eliminated")
        role == Role.CIVILIAN -> Triple(RoleCivilian.copy(alpha = 0.15f), RoleCivilian, role.displayName)
        role == Role.UNDERCOVER -> Triple(RoleUndercover.copy(alpha = 0.15f), RoleUndercover, role.displayName)
        role == Role.MR_WHITE -> Triple(RoleMrWhite.copy(alpha = 0.15f), RoleMrWhite, role.displayName)
        else -> Triple(SurfaceContainer, TextMuted, role.displayName)
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(bgColor)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = textColor,
            fontFamily = FontFamily.Monospace
        )
    }
}

@Composable
fun ConfirmationModal(
    title: String,
    message: String,
    confirmText: String,
    confirmButtonColor: Color = StateElimination,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SurfacePure)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextPrimary,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextMuted,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = onConfirm,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = confirmButtonColor),
                    shape = RoundedCornerShape(26.dp)
                ) {
                    Text(
                        text = confirmText,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Cancel",
                        color = TextPrimary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
fun MrWhiteGuessModal(
    playerName: String,
    guessInput: String,
    onGuessInputChange: (String) -> Unit,
    onSubmitGuess: () -> Unit,
    onSkip: () -> Unit,
    errorMessage: String? = null
) {
    Dialog(onDismissRequest = {}) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SurfacePure)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RoleBadge(role = Role.MR_WHITE)
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "$playerName's Last Stand",
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextPrimary,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "$playerName was Mr White! Guess the Civilian secret word correctly to snatch victory!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextMuted,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = guessInput,
                    onValueChange = onGuessInputChange,
                    label = { Text("Enter secret word") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = TextPrimary,
                        unfocusedBorderColor = BorderSubtle
                    ),
                    singleLine = true
                )

                if (errorMessage != null) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = errorMessage,
                        color = StateElimination,
                        fontSize = 13.sp
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = onSubmitGuess,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TextPrimary),
                    shape = RoundedCornerShape(26.dp),
                    enabled = guessInput.isNotBlank()
                ) {
                    Text(
                        text = "Submit Guess →",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(
                    onClick = onSkip,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Skip / Don't know",
                        color = TextMuted,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}
