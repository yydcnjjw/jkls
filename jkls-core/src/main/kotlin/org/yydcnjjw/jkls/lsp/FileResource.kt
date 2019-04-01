package org.yydcnjjw.jkls.lsp

data class CreateFileOptions(
    val overwrite: Boolean?,
    val ignoreIfExists: Boolean?
)

data class CreateFile(
    val kind: String = "create",
    val uri: String,
    val options: CreateFileOptions?
)

data class RenameFileOptions(
    val overwrite: Boolean?,
    val ignoreIfExists: Boolean?
)

data class RenameFile(
    val kind: String = "rename",
    val oldUri: String,
    val newUri: String,
    val options: RenameFileOptions?
)

data class DeleteFileOptions(
    val recursive: Boolean?,
    val ignoreIfNotExists: Boolean?
)

data class DeleteFile(
    val kind: String = "delete",
    val uri: String,
    val options: DeleteFileOptions?
)