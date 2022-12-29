package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DemoApplicationTests {

    @Test
    public void contextLoads() {
        String a = "SELECT\tscc.cost_id,\tscc.cost_code data_code,\tscc.cost_name data_nameFROM\tsmdm_cost_center scc \tLEFT JOIN hpfm_company hc ON hc.company_id = scc.company_idWHERE\tscc.tenant_id = #{request.tenantId}\tAND hc.company_num = 'CO00000003'\tAND scc.enabled_flag = 1\t<if test='dataCode != null and dataCode != \"\"'>\t\t<bind name=\"dataCodeLike\" value=\"'%' + dataCode + '%'\"/>\t\tAND scc.cost_code LIKE #{dataCodeLike}\t</if>\t<if test='dataName != null and dataName != \"\"'>\t\t<bind name=\"dataNameLike\" value=\"'%' + dataName + '%'\"/>\t\tAND scc.cost_code LIKE #{dataNameLike}\t</if>";
        String b = "SELECT\tscc.cost_id,\tscc.cost_code data_code,\tscc.cost_name data_nameFROM\tsmdm_cost_center scc \tLEFT JOIN hpfm_company hc ON hc.company_id = scc.company_idWHERE\tscc.tenant_id = #{request.tenantId}\tAND hc.company_num = 'CO00000003'\tAND scc.enabled_flag = 1\t<if test='dataCode != null and dataCode != \"\"'>\t\t<bind name=\"dataCodeLike\" value=\"'%' + dataCode + '%'\"/>\t\tAND scc.cost_code LIKE #{dataCodeLike}\t</if>\t<if test='dataName != null and dataName != \"\"'>\t\t<bind name=\"dataNameLike\" value=\"'%' + dataName + '%'\"/>\t\tAND scc.cost_code LIKE #{dataNameLike}\t</if>";

        System.out.println(a.replaceAll(" ", "")
                .replaceAll("\n", "")
                .replaceAll("\t", "")
                .equals(b.replaceAll(" ", "")
                        .replaceAll("\n", "")
                        .replaceAll("\t", "")));
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void homeResponse() {
        String body = this.restTemplate.getForObject("/", String.class);
        assertThat(body).isEqualTo("Spring is here!");
    }
}
