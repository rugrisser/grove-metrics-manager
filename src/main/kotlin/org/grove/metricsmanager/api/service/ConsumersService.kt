package org.grove.metricsmanager.api.service

import org.grove.metricsmanager.common.dao.ConsumersDao
import org.springframework.stereotype.Service

@Service
class ConsumersService(
    private val consumersDao: ConsumersDao
) {


}
