package org.yydcnjjw.jkls.lsp.message

import org.yydcnjjw.jkls.lsp.DocumentSelector

enum class ClientMethod(val value: String) {
    REGISTER_CAPABILITY("client/registerCapability"),
    UNREGISTER_CAPABILITY("client/unregisterCapability")
}

/**
 * Register Capability Request
 * @method 'client/registerCapability'
 * @param 'RegistrationParams'
 *
 * Response:
 * @result 'void'
 */
data class Registration(
    val id: String,
    val method: String,
    val registerOptions: Any?
)

data class RegistrationParams(
    val registrations: List<Registration>
)

abstract class TextDocumentRegistrationOptions(
    open val documentSelector: DocumentSelector?
)

/**
 * Unregister Capability Request
 * @method 'client/unregisterCapability'
 * @param 'UnregistrationParams'
 *
 * Response:
 * @result 'void'
 */

data class Unregistration(
    val id: String,
    val method: String
)

data class UnregistrationParams(
    val unregistrations: List<Unregistration>
)