/*
 *  Twidere X
 *
 *  Copyright (C) 2020-2021 Tlaster <tlaster@outlook.com>
 * 
 *  This file is part of Twidere X.
 * 
 *  Twidere X is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  Twidere X is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with Twidere X. If not, see <http://www.gnu.org/licenses/>.
 */
package com.twidere.twiderex.component.status

import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope.Companion.weight
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.twidere.twiderex.R
import com.twidere.twiderex.action.LocalStatusActions
import com.twidere.twiderex.component.foundation.ActionIconButton
import com.twidere.twiderex.component.navigation.LocalNavigator
import com.twidere.twiderex.extensions.humanizedCount
import com.twidere.twiderex.extensions.shareText
import com.twidere.twiderex.model.PlatformType
import com.twidere.twiderex.model.ui.UiStatus
import com.twidere.twiderex.ui.LocalActiveAccount
import com.twidere.twiderex.ui.standardPadding
import com.twidere.twiderex.ui.statusActionIconSize
import com.twidere.twiderex.viewmodel.compose.ComposeType

private val rippleSize = 24.dp

@Composable
fun ReplyButton(
    status: UiStatus,
    withNumber: Boolean = true,
) {
    val navigator = LocalNavigator.current
    val icon = painterResource(id = R.drawable.ic_corner_up_left)
    val contentDescription = stringResource(id = R.string.accessibility_common_status_actions_reply)
    val action = {
        navigator.compose(ComposeType.Reply, statusKey = status.statusKey)
    }
    if (withNumber) {
        StatusActionButtonWithNumbers(
            icon = icon,
            color = LocalContentColor.current,
            count = status.replyCount,
            contentDescription = contentDescription,
            onClick = {
                action.invoke()
            },
        )
    } else {
        ActionIconButton(
            onClick = {
                action.invoke()
            },
        ) {
            Icon(
                painter = icon,
                tint = LocalContentColor.current,
                contentDescription = contentDescription,
            )
        }
    }
}

@Composable
fun LikeButton(
    status: UiStatus,
    withNumber: Boolean = true,
) {
    val actionsViewModel = LocalStatusActions.current
    val account = LocalActiveAccount.current
    val color = if (status.liked) {
        Color.Red
    } else {
        LocalContentColor.current
    }
    val contentDescription = stringResource(id = R.string.accessibility_common_status_actions_like)
    val icon = painterResource(id = R.drawable.ic_heart)
    val action = {
        if (account != null) {
            actionsViewModel.like(status, account)
        }
    }
    if (withNumber) {
        StatusActionButtonWithNumbers(
            icon = icon,
            count = status.likeCount,
            color = color,
            contentDescription = contentDescription,
            onClick = {
                action.invoke()
            },
        )
    } else {
        ActionIconButton(
            onClick = {
                action.invoke()
            },
        ) {
            Icon(
                painter = icon,
                tint = color,
                contentDescription = contentDescription,
            )
        }
    }
}

@Composable
fun RetweetButton(
    status: UiStatus,
    withNumber: Boolean = true,
) {
    val actionsViewModel = LocalStatusActions.current
    val account = LocalActiveAccount.current
    val color = if (status.retweeted) {
        MaterialTheme.colors.primary
    } else {
        LocalContentColor.current
    }
    val icon = painterResource(id = R.drawable.ic_repeat)
    val contentDescription =
        stringResource(id = R.string.accessibility_common_status_actions_retweet)
    var expanded by remember { mutableStateOf(false) }
    val retweetAction = {
        if (status.platformType == PlatformType.Twitter) {
            expanded = true
        } else {
            if (account != null) {
                actionsViewModel.retweet(status = status, account = account)
            }
        }
    }
    Box(
        modifier = Modifier.let {
            if (withNumber) {
                it.weight(1f)
            } else {
                it
            }
        }
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            DropdownMenuItem(
                onClick = {
                    if (account != null) {
                        actionsViewModel.retweet(status, account)
                    }
                    expanded = false
                }
            ) {
                Text(text = stringResource(id = R.string.common_controls_status_actions_retweet))
            }
            val navigator = LocalNavigator.current
            DropdownMenuItem(
                onClick = {
                    navigator.compose(ComposeType.Quote, statusKey = status.statusKey)
                }
            ) {
                Text(
                    text = stringResource(id = R.string.common_controls_status_actions_quote),
                )
            }
        }
        if (withNumber) {
            StatusActionButtonWithNumbers(
                icon = icon,
                count = status.retweetCount,
                color = color,
                contentDescription = contentDescription,
                onClick = {
                    retweetAction.invoke()
                },
            )
        } else {
            ActionIconButton(
                onClick = {
                    retweetAction.invoke()
                },
            ) {
                Icon(
                    painter = icon,
                    tint = color,
                    contentDescription = contentDescription,
                )
            }
        }
    }
}

@Composable
fun ShareButton(
    status: UiStatus,
    compat: Boolean = false,
) {
    var expanded by remember { mutableStateOf(false) }
    val actionsViewModel = LocalStatusActions.current
    val account = LocalActiveAccount.current
    val accountKey = account?.accountKey
    val context = LocalContext.current
    val icon = Icons.Default.MoreHoriz
    val text = renderContentAnnotatedString(
        htmlText = status.htmlText,
        linkResolver = { status.resolveLink(it) },
    )
    val clipboardManager = LocalClipboardManager.current
    val contentDescription = stringResource(id = R.string.accessibility_common_more)
    Box {
        if (compat) {
            Box(
                modifier = Modifier
                    .defaultMinSize(
                        minHeight = ButtonDefaults.MinHeight
                    )
                    .clickable(
                        indication = rememberRipple(
                            bounded = false,
                            radius = rippleSize
                        ),
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = {
                            expanded = true
                        },
                    )
                    .padding(
                        top = ButtonDefaults.ContentPadding.calculateTopPadding(),
                        bottom = ButtonDefaults.ContentPadding.calculateBottomPadding(),
                        start = standardPadding,
                        end = standardPadding,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    modifier = Modifier.size(statusActionIconSize),
                    imageVector = icon,
                    contentDescription = contentDescription,
                    tint = LocalContentColor.current.copy(LocalContentAlpha.current)
                )
            }
        } else {
            ActionIconButton(
                onClick = {
                    expanded = true
                },
            ) {
                Icon(
                    imageVector = icon,
                    tint = LocalContentColor.current,
                    contentDescription = contentDescription,
                )
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    clipboardManager.setText(text)
                }
            ) {
                Text(
                    text = stringResource(id = R.string.common_controls_status_actions_copy_text),
                )
            }
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    clipboardManager.setText(
                        buildAnnotatedString {
                            append(status.generateShareLink())
                        }
                    )
                }
            ) {
                Text(
                    text = stringResource(id = R.string.common_controls_status_actions_copy_link),
                )
            }
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    context.shareText(status.generateShareLink())
                }
            ) {
                Text(
                    text = stringResource(id = R.string.common_controls_status_actions_share_link),
                )
            }
            if (status.user.userKey == accountKey) {
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        actionsViewModel.delete(status, account)
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.common_controls_actions_remove),
                        color = Color.Red,
                    )
                }
            }
        }
    }
}

@Composable
private fun StatusActionButtonWithNumbers(
    modifier: Modifier = Modifier,
    icon: Painter,
    contentDescription: String,
    count: Long,
    enabled: Boolean = true,
    color: Color = LocalContentColor.current,
    onClick: () -> Unit,
) {
    val contentColor = color.copy(LocalContentAlpha.current)
    Box(
        modifier = modifier
            .weight(1f),
    ) {
        val source = remember {
            MutableInteractionSource()
        }
        Row(
            modifier = Modifier
                .defaultMinSize(
                    minHeight = ButtonDefaults.MinHeight
                )
                .clickable(
                    onClick = onClick,
                    enabled = enabled,
                    interactionSource = source,
                    indication = null,
                )
                .padding(
                    top = ButtonDefaults.ContentPadding.calculateTopPadding(),
                    bottom = ButtonDefaults.ContentPadding.calculateBottomPadding(),
                    start = standardPadding,
                    end = standardPadding,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier
                    .size(MaterialTheme.typography.body1.fontSize.value.dp)
                    .indication(
                        source,
                        rememberRipple(
                            bounded = false,
                            radius = rippleSize
                        )
                    ),
                tint = contentColor,
                painter = icon,
                contentDescription = contentDescription,
            )
            Row(
                modifier = modifier
                    .weight(1f),
            ) {
                if (count > 0) {
                    Box(modifier = Modifier.width(4.dp))
                    Text(
                        text = count.humanizedCount(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.body2,
                        color = contentColor,
                    )
                }
            }
        }
    }
}
