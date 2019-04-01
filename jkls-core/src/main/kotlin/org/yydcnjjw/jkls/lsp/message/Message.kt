package org.yydcnjjw.jkls.lsp.message

// Content-Length: ...\r\n
// \r\n
// {
//     "jsonrpc": "2.0",
//     "id": 1,
//     "method": "textDocument/didOpen",
//     "params": {
//         ...
//     }
// }

abstract class Message {
    var jsonrpc: String = "2.0"
}

enum class MessageMethodType {
    REQUEST,
    RESPONSE,
    NOTIFICATION
}

data class RequestMessage<P>(
    val id: Any,
    val method: String,
    val params: P?
) : Message()

data class ResponseMessage <R, D> (
    val id: Any,
    val result: R?,
    val error: ResponseError<D>?
) : Message()

data class NotificationMessage<T>(
    val method: String,
    val params: T?
) : Message()

data class ResponseError<D>(
    val code: Int,
    val message: String,
    val data: D?
)

enum class ErrorCode(val code: Int) {
    // Defined by JSON RPC
    ParseError(-32700),
    InvalidRequest(-32600),
    MethodNotFound(-32601),
    InvalidParams(-32602),
    InternalError(-32603),
    ServerErrorStart(-32099),
    ServerErrorEnd(-32000),
    ServerNotInitialized(-32002),
    UnknownErrorCode(-32001),

    // Defined by the protocol.
    RequestCancelled(-32800),
    ContentModified(-32801)
}