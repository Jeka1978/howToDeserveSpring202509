package com.borisov.howtodeservespring.infra.epp.less3spiders;

import com.borisov.howtodeservespring.PaperSpider;
import com.borisov.howtodeservespring.Spider;
import lombok.experimental.Delegate;

public class Spider0 implements Spider {
    @Delegate PaperSpider paperSpider;
}
