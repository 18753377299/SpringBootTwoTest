package com.example.test;

import com.example.SpringBootTwoJpaApplication;
import com.example.common.readconfig.UtiKeyProperties;
import com.example.common.vo.UtiKey;
import com.example.dao.*;
import com.example.pojo.RiskReportSaleImaType;
import com.example.pojo.RiskReportSaleMain;
import com.example.pojo.Users;
import com.example.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * 测试类
 *
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootTwoJpaApplication.class)
public class UsersRepositoryTest {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private UsersRepositoryByName usersRepositoryByName;

	@Autowired
	private UsersRepositoryQueryAnnotation usersRepositoryQueryAnnotation;
	
	@Autowired
	private UsersRepositoryCrudRepository usersRepositoryCrudRepository;
	
	@Autowired
	private UsersRepositoryPagingAndSorting usersRepositoryPagingAndSorting;
	
	@Autowired
	private UsersRepositorySpecification usersRepositorySpecification;
	
	@Autowired
	TestService testService;
	
	@Autowired
	private  UtiKeyProperties utiKeyProperties;
	
	@Test
	public void query() {
		testService.findAll();
	}
	
	@Test
	public void testSave() {
		List<UtiKey> utiKeys =  utiKeyProperties.getUtikeys();
		System.out.println(utiKeys.toString());
		
		Users users = new Users();
		users.setAddress("上海市");
		users.setAge(24);
		users.setName("王五");
		this.usersRepository.save(users);
	}

	/**
	 * Repository--方法名称命名测试
	 */
	@Test
	public void testFindByName() {
		List<Users> list = this.usersRepositoryByName.findByName("张三");
		for (Users users : list) {
			System.out.println(users);
		}
	}

	/**
	 * Repository--方法名称命名测试
	 */
	@Test
	public void testFindByNameAndAge() {
		List<Users> list = this.usersRepositoryByName.findByNameAndAge("张三", 20);
		for (Users users : list) {
			System.out.println(users);
		}
	}

	/**
	 * Repository--方法名称命名测试
	 */
	@Test
	public void testFindByNameLike() {
//		List<Users> list = this.usersRepositoryByName.findByNameLike("张%");
//		for (Users users : list) {
//			System.out.println(users);
//		}
	}

	/**
	 * Repository--@Query测试
	 */
	@Test
	public void testQueryByNameUseHQL() {
		List<Users> list = this.usersRepositoryQueryAnnotation.queryByNameUseHQL("张三");
		for (Users users : list) {
			System.out.println(users);
		}
	}

	/**
	 * Repository--@Query测试
	 */
	@Test
	public void testQueryByNameUseSQL() {
//		List<Users> list = this.usersRepositoryQueryAnnotation.queryByNameUseSQL("张三");
//		for (Users users : list) {
//			System.out.println(users);
//		}
	}

	/**
	 * Repository--@Query测试
	 * //@Transactional与@Test 一起使用时 事务是自动回滚的。 所以数据是不会插入到数据库中，增加@Rollback(false)之后可以成功的更新
	 */
	@Test
	@Transactional 
	@Rollback(false) //取消自动回滚
	public void testUpdateUsersNameById() {
//		this.usersRepositoryQueryAnnotation.updateUsersNameById("张三三", 1);
	}
	
	/**
	 * CrudRepository测试
	 */
	@Test
	public void testCrudRepositorySave() {
		Users user = new Users();
		user.setAddress("天津");
		user.setAge(32);
		user.setName("张三丰");
		this.usersRepositoryCrudRepository.save(user);
	}
	
	/**
	 * CrudRepository测试
	 */
	@Test
	public void testCrudRepositoryUpdate() {
		Users user = new Users();
		user.setId(4);
		user.setAddress("南京");
		user.setAge(40);
		user.setName("张三丰");
		this.usersRepositoryCrudRepository.save(user);
	}
	
	/**
	 * CrudRepository测试
	 */
	@Test
	public void testCrudRepositoryFindOne() {
//		Users users = this.usersRepositoryCrudRepository.findOne(4);
		Optional<Users> optional = this.usersRepositoryCrudRepository.findById(4);
		Users users =  optional.isPresent()?optional.get():null;
		System.out.println(users);
	}
	
	/**
	 * CrudRepository测试
	 */
	@Test
	public void testCrudRepositoryFindAll() {
		List<Users> list  =  (List<Users>)this.usersRepositoryCrudRepository.findAll();
		for (Users users : list) {
			System.out.println(users);
		}
	}
	
	/**
	 * CrudRepository测试
	 */
	@Test
	public void testCrudRepositoryDeleteById() {
//		this.usersRepositoryCrudRepository.delete(4);
//		usersRepositoryCrudRepository.deleteById(4);
	}
	
	/**
	 * PagingAndSortingRepository   排序测试
	 */
	@Test
	public void testPagingAndSortingRepositorySort() {
		//Order 定义排序规则
		Order order = new Order(Direction.DESC,"id");
		//Sort对象封装了排序规则
		Sort sort = new Sort(order);
		List<Users> list = (List<Users>)this.usersRepositoryPagingAndSorting.findAll(sort);
		for (Users users : list) {
			System.out.println(users);
		}
	}
	
	/**
	 * PagingAndSortingRepository   分页测试
	 */
	@Test
	public void testPagingAndSortingRepositoryPaging() {
		//Pageable:封装了分页的参数，当前页，每页显示的条数。注意：他的当前页是从0开始。
		//PageRequest(page,size) page:当前页。size:每页显示的条数
		Pageable pageable = new PageRequest(1, 2);
	    Page<Users> page = this.usersRepositoryPagingAndSorting.findAll(pageable);
		System.out.println("总条数："+page.getTotalElements());
		System.out.println("总页数"+page.getTotalPages());
		List<Users> list = page.getContent();
		for (Users users : list) {
			System.out.println(users);
		}
	}
	
	/**
	 * PagingAndSortingRepository   排序+分页
	 */
	@Test
	public void testPagingAndSortingRepositorySortAndPaging() {
		
		Sort sort = new Sort(new Order(Direction.DESC, "id"));
		
		Pageable pageable = new PageRequest(1, 2, sort);
				
		Page<Users> page = this.usersRepositoryPagingAndSorting.findAll(pageable);
		System.out.println("总条数："+page.getTotalElements());
		System.out.println("总页数"+page.getTotalPages());
		List<Users> list = page.getContent();
		for (Users users : list) {
			System.out.println(users);
		}
	}
	
	/**
	 * JapRepository   排序测试
	 */
	@Test
	public void testJpaRepositorySort() {
		//Order 定义排序规则
		Order order = new Order(Direction.DESC,"id");
		//Sort对象封装了排序规则
		Sort sort = new Sort(order);
		List<Users> list = this.usersRepository.findAll(sort);
		for (Users users : list) {
			System.out.println(users);
		}
	}
	
	/**
	 * JpaSpecificationExecutor   单条件测试
	 */
	@Test
	public void testJpaSpecificationExecutor1() {
		
		/**
		 * Specification<Users>:用于封装查询条件
		 */
		Specification<Users> spec = new Specification<Users>() {
			
			//Predicate:封装了 单个的查询条件
			/**
			 * Root<Users> root:查询对象的属性的封装。
			 * CriteriaQuery<?> query：封装了我们要执行的查询中的各个部分的信息，select  from order by
			 * CriteriaBuilder cb:查询条件的构造器。定义不同的查询条件
			 */
			@Override
			public Predicate toPredicate(Root<Users> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// where name = '张三三'
				/**
				 * 参数一：查询的条件属性
				 * 参数二：条件的值
				 */
				Predicate pre = cb.equal(root.get("name"), "张三三");
				return pre;
			}
		};
		System.out.println(spec.toString());
		List<Users> list = this.usersRepositorySpecification.findAll(spec);
		for (Users users : list) {
			System.out.println(users);
		}
	}
	
	
	/**
	 * JpaSpecificationExecutor   多条件测试
	 */
	@Test
	public void testJpaSpecificationExecutor2() {
		
		/**
		 * Specification<Users>:用于封装查询条件
		 */
		Specification<Users> spec = new Specification<Users>() {
			
			//Predicate:封装了 单个的查询条件
			/**
			 * Root<Users> root:查询对象的属性的封装。
			 * CriteriaQuery<?> query：封装了我们要执行的查询中的各个部分的信息，select  from order by
			 * CriteriaBuilder cb:查询条件的构造器。定义不同的查询条件
			 */
			@Override
			public Predicate toPredicate(Root<Users> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// where name = '张三三' and age = 20
				List<Predicate> list = new ArrayList<>();
//				list.add(cb.equal(root.get("name"),"张三三"));
//				list.add(cb.equal(root.get("age"),20));
				// 进行字段截取查询
//				list.add(cb.equal(cb.substring(root.get("name"), 1,1),"张"));
				
				list.add(cb.isNotNull(root.get("name")));
				
				
				Predicate[] arr = new Predicate[list.size()];
//				query.where();
				return cb.and(list.toArray(arr));
			}
		};
		List<Users> list = this.usersRepositorySpecification.findAll(spec);
		for (Users users : list) {
			System.out.println(users);
		}
	}
	
	/**
	 * JpaSpecificationExecutor   多条件测试第二种写法
	 */
	@Test
	public void testJpaSpecificationExecutor3() {
		
		/**
		 * Specification<Users>:用于封装查询条件
		 */
		Specification<Users> spec = new Specification<Users>() {
			
			//Predicate:封装了 单个的查询条件
			/**
			 * Root<Users> root:查询对象的属性的封装。
			 * CriteriaQuery<?> query：封装了我们要执行的查询中的各个部分的信息，select  from order by
			 * CriteriaBuilder cb:查询条件的构造器。定义不同的查询条件
			 */
			@Override
			public Predicate toPredicate(Root<Users> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// where name = '张三三' and age = 20
				/*List<Predicate> list = new ArrayList<>();
				list.add(cb.equal(root.get("name"),"张三三"));
				list.add(cb.equal(root.get("age"),20));
				Predicate[] arr = new Predicate[list.size()];*/
				//(name = '张三' and age = 20) or id = 2
				return cb.or(cb.and(cb.equal(root.get("name"),"张三三"),cb.equal(root.get("age"),20)),cb.equal(root.get("id"), 2));
			}
		};
		
		Sort sort = new Sort(new Order(Direction.DESC,"id"));
		List<Users> list = this.usersRepositorySpecification.findAll(spec,sort);
		for (Users users : list) {
			System.out.println(users);
		}
	}
	
	@Autowired
	private RiskReportSaleMainRepository riskReportSaleMainRepository;
	
	@Test
	public void getListByPager(){
		String riskFileNo ="11111";
		/*,int iDisplayStart,
        int iDisplayLength,int iSortCol_0,String sSortDir_0*/
//		    Pageable pagerequest = buildPageRequest(iDisplayStart, iDisplayLength, iSortCol_0, sSortDir_0);
		Sort sort = new Sort(Direction.ASC, "archivesNo");
		Pageable pagerequest = new PageRequest(0, 2, sort);
		
	    Page<RiskReportSaleMain> page = null;
		try {
			page = riskReportSaleMainRepository.findAll(new Specification<RiskReportSaleMain>(){
			    @Override
			    public Predicate toPredicate(Root<RiskReportSaleMain> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
			    	List<Predicate> predicates = new ArrayList<>();
			    	predicates.add(cb.greaterThan(root.get("exploreComcode"), "00000000"));
			    	
			        //左连接
			        root.join(root.getModel().getSingularAttribute("riskReportSaleImaType",RiskReportSaleImaType.class),JoinType.LEFT);
			        Predicate likeP1 = cb.like(root.get("archivesNo").as(String.class),"%"+riskFileNo+"%");
//		            Predicate likeP2 = cb.like(depJoin.get("businessNumber").as(String.class), "%"+sSearch+"%");
			        Predicate likeP2 = cb.like(root.get("archivesNo").as(String.class), "%"+riskFileNo+"%");
			        Predicate orP = cb.or(likeP1,likeP2);
			        //参考： http://www.iteye.com/problems/92130
			        return cb.and(cb.conjunction(),orP);
//			        predicates.add(likeP1);
//			        predicates.add(likeP2);
//			        predicates.add(orP);
//			        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			    }
			},pagerequest);
		} catch (Exception e) {
			e.printStackTrace();
		}

	    //pagerequest = page.nextPageable();
	    System.out.println(page);
//	    return page;
//	    return null;
	}
	/**
	 * jpa进行联表查询，在join联表查询之前的条件是主表的条件，join之后的条件属于子表的条件，也可以进行多个表的连接查询。
	 * riskReportSaleImaType:是主表中对应的联结表的名称。
	 *   试验成功！ add by liqiankun 20201020 begin 
	 *   详情见：https://blog.csdn.net/cn_hhaip/article/details/89527666
	 * */
	@Test
	public void unionFormQueryTest() {
		
		List<RiskReportSaleMain> list;
		try {
			Specification<RiskReportSaleMain> cation = (root, criteriaQuery, builder) -> {
			    List<Predicate> predicates = new ArrayList<>();
              //在关联查出努努
	        predicates.add(builder.greaterThan(root.get("exploreComcode"), "00000000"));
//	        predicates.add(builder.equal(root.get("status"), 0).not());
				
				/**ErrorRender故障表和Vm表进行连接查询，在ErrorRender必须有一个private Vm vm属性*/
				//相当于 select * from error_render e left join vm v on e.vm=v.vm
			    Join<RiskReportSaleMain, RiskReportSaleImaType> vmJoin = root.join("riskReportSaleImaType", JoinType.LEFT);

			    //机型条件 相当于 v.vmTypeId >= 0
//	        if (vmTypeId >= 0) {
//			        predicates.add(builder.equal(vmJoin.get("archivesno"), "11111"));
//	        }
				
				// 相当于(left on node n on v.node_id=n.node_id) as tmp left join org o on o.org_id= tmp.org_id
//	        Join<Vms, Orgas> orgJoin = vmJoin.join("node", JoinType.LEFT).join("org", JoinType.LEFT);

			    //带有orgId查询
//	        Orgas org = orgasRepository.findOne(orgId);
				//相当于 o.hierarchy like org.getHierarchy() + "%"
//	        predicates.add(builder.like(orgJoin.get("hierarchy"), org.getHierarchy() + "%"));

//	        if (error != null && error != 0) {
//	            predicates.add(root.get("error").in(allErrorList));
//	        } 

			    if (predicates.size() > 1) {
			        return builder.and(predicates.toArray(new Predicate[predicates.size()]));
			    } else if (predicates.size() == 1) {
			        return predicates.get(0);
			    } else {
			        return null;
			    }
			};
			Sort sort = new Sort(new Order(Direction.DESC,"archivesNo"));
			list = this.riskReportSaleMainRepository.findAll(cation,sort);
			System.out.println(list.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
	
	
}
