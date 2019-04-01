package org.yydcnjjw.jkls.lsp.message

import org.yydcnjjw.jkls.lsp.DocumentUri
import org.yydcnjjw.jkls.lsp.WorkspaceEdit

enum class WorkspaceMethod(val value: String) {
    WORKSPACE_FOLDERS("workspace/workspaceFolders’"),
    DID_CHANGE_WORKSPACE_FOLDERS("workspace/didChangeWorkspaceFolders"),
    DID_CHANGE_CONFIGURATION("workspace/didChangeConfiguration"),
    CONFIGURATION("workspace/configuration"),
    DID_CHANGE_WATCHED_FILES("workspace/didChangeWatchedFiles"),
    SYMBOL("workspace/symbol’"),
    EXECUTE_COMMAND("workspace/executeCommand"),
    APPLY_EDIT("workspace/applyEdit")
}

/**
 * Workspace folders request
 * @method 'workspace/workspaceFolders'
 * @param 'none'
 *
 * Response
 * @result 'WorkspaceFolder[] | null'
 */

data class WorkspaceFolder(
    val uri: String,
    val name: String
)

/**
 * DidChangeWorkspaceFolders Notification
 * @method 'workspace/didChangeWorkspaceFolders'
 * @param 'DidChangeWorkspaceFoldersParams'
 */

data class DidChangeWorkspaceFoldersParams(
    val event: WorkspaceFoldersChangeEvent
)

data class WorkspaceFoldersChangeEvent(
    val added: List<WorkspaceFolder>,
    val removed: List<WorkspaceFolder>
)


/**
 * DidChangeConfiguration Notification
 * @method 'workspace/didChangeConfiguration'
 * @param 'DidChangeConfigurationParams'
 */

data class DidChangeConfigurationParams(
    val settings: Any
)

/**
 * Configuration Request
 * @method 'workspace/configuration'
 * @param 'ConfigurationParams'
 *
 * Response
 * @result: 'any[]'
 */

data class ConfigurationParams(
    val items: List<ConfigurationItem>
)
data class ConfigurationItem(
    val scopeUri: String?,
    val section: String?
)

/**
 * DidChangeWatchedFiles Notification
 * @method 'workspace/didChangeWatchedFiles'
 * @param 'DidChangeWatchedFilesParams'
 */

data class DidChangeWatchedFilesParams(
    val changes: FileEvent
)

data class FileEvent(
    val uri: DocumentUri,
    val type: FileChangeType
)

enum class FileChangeType(val type: Int) {
    Created(1),
    Changed(2),
    Deleted(3)
}

data class DidChangeWatchedFilesRegistrationOptions(
    val watchers: List<FileSystemWatcher>
)

data class FileSystemWatcher(
    val globPattern: String,
    val kind: WatchKind?
)

enum class WatchKind(val kind: Int) {
    Created(1),
    Changed(2),
    Deleted(3)
}

/**
 * Workspace Symbols Request
 * @method 'workspace/symbol'
 * @param 'WorkspaceSymbolParams'
 *
 * Response
 * @result 'SymbolInformation[] | null'
 */

data class WorkspaceSymbolParams(
    val query: String
)

/**
 * Execute command Request
 * @method 'workspace/executeCommand'
 * @param 'ExecuteCommandParams'
 *
 * Response
 * @result 'any | null'
 */

data class ExecuteCommandParams(
    val command: String,
    val arguments: List<Any>?
)

data class ExecuteCommandRegistrationOptions(
    val command: List<String>
)

/**
 * Applies workspaceEdit Request
 * @method 'workspace/applyEdit'
 * @param 'ApplyWorkspaceEditParams'
 *
 * Response
 * @result 'ApplyWorkspaceEditResponse'
 */

data class ApplyWorkspaceEditParams(
    val label: String?,
    val edit: WorkspaceEdit
)

data class ApplyWorkspaceEditResponse(
    val applied: Boolean
)