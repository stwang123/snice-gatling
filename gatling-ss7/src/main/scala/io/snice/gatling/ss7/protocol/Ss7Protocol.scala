package io.snice.gatling.ss7.protocol

import io.gatling.core.config.GatlingConfiguration
import io.gatling.core.protocol.{Protocol, ProtocolKey}
import io.gatling.core.{CoreComponents, protocol}
import io.snice.gatling.ss7.engine.Ss7Client

object Ss7Protocol {
  val ss7ProtocolKey: ProtocolKey[Ss7Protocol, Ss7Components] = new ProtocolKey[Ss7Protocol, Ss7Components] {
    override def protocolClass: Class[protocol.Protocol] = classOf[Ss7Protocol].asInstanceOf[Class[io.gatling.core.protocol.Protocol]]

    override def defaultProtocolValue(configuration: GatlingConfiguration): Ss7Protocol = {
      Ss7Protocol()
    }

    override def newComponents(coreComponents: CoreComponents): Ss7Protocol => Ss7Components = {
      protocol => {
        val client = Ss7Client(protocol.ss7Config)
        Ss7Components(protocol, client)
      }
    }
  }

  def apply(): Ss7Protocol = new Ss7Protocol(new Ss7Config)
}

case class Ss7Protocol(ss7Config: Ss7Config) extends Protocol